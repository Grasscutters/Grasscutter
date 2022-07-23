# Written for Python 3.6+
# Older versions don't retain insertion order of regular dicts
import argparse
import cmd
import json
import os
import re
from pprint import pprint

INDENT = 2
PRIMARY_LANGUAGE = 'en-US.json'
PRIMARY_FALLBACK_PREFIX = '🇺🇸'  # This is invisible in-game, terminal emulators might render it
LANGUAGE_FOLDER = 'src/main/resources/languages/'
LANGUAGE_FILENAMES = sorted(os.listdir(LANGUAGE_FOLDER), key=lambda x: 'AAA' if x == PRIMARY_LANGUAGE else x)
SOURCE_FOLDER = 'src/'
SOURCE_EXTENSIONS = ('java')


def ppprint(data):
    pprint(data, width=130, sort_dicts=False, compact=True)


class JsonHelpers:
    @staticmethod
    def load(filename: str) -> dict:
        with open(filename, 'r') as file:
            return json.load(file)

    @staticmethod
    def save(filename: str, data: dict) -> None:
        with open(filename, 'w', encoding='utf-8', newline='\n') as file:
            json.dump(data, file, ensure_ascii=False, indent=INDENT)

    @staticmethod
    def flatten(data: dict, prefix='') -> dict:
        output = {}
        for key, value in data.items():
            if isinstance(value, dict):
                for k,v in JsonHelpers.flatten(value, f'{prefix}{key}.').items():
                    output[k] = v
            else:
                output[f'{prefix}{key}'] = value
        return output

    @staticmethod
    def unflatten(data: dict) -> dict:
        output = {}
        def add_key(k: list, value, d: dict):
            if len(k) == 1:
                d[k[0]] = value
            else:
                d[k[0]] = d.get(k[0], {})
                add_key(k[1:], value, d[k[0]])
        for key, value in data.items():
            add_key(key.split('.'), value, output)
        return output

    @staticmethod
    def pprint_keys(keys, indent=4) -> str:
        # Only strip down to one level
        padding = ' ' * indent
        roots = {}
        for key in keys:
            root, _, k = key.rpartition('.')
            roots[root] = roots.get(root, [])
            roots[root].append(k)
        lines = []
        for root, ks in roots.items():
            if len(ks) > 1:
                lines.append(f'{padding}{root}.[{", ".join(ks)}]')
            else:
                lines.append(f'{padding}{root}.{ks[0]}')
        return ',\n'.join(lines)

    @staticmethod
    def deep_clone_and_fill(d1: dict, d2: dict, fallback_prefix=PRIMARY_FALLBACK_PREFIX) -> dict:
        out = {}
        for key, value in d1.items():
            if isinstance(value, dict):
                out[key] = JsonHelpers.deep_clone_and_fill(value, d2.get(key, {}), fallback_prefix)
            else:
                v2 = d2.get(key, value)
                if type(value) == str and v2 == value:
                    out[key] = fallback_prefix + value
                else:
                    out[key] = v2
        return out


class LanguageManager:
    TRANSLATION_KEY = re.compile(r'[Tt]ranslate.*"(\w+\.[\w\.]+)"')
    POTENTIAL_KEY = re.compile(r'"(\w+\.[\w\.]+)"')
    COMMAND_LABEL = re.compile(r'@Command\s*\([\W\w]*?label\s*=\s*"(\w+)"', re.MULTILINE)  # [\W\w] is a cheeky way to match everything including \n

    def __init__(self):
        self.load_jsons()

    def load_jsons(self):
        self.language_jsons = [JsonHelpers.load(LANGUAGE_FOLDER + filename) for filename in LANGUAGE_FILENAMES]
        self.flattened_jsons = [JsonHelpers.flatten(j) for j in self.language_jsons]
        self.update_keys()

    def update_keys(self):
        self.key_sets = [set(j.keys()) for j in self.flattened_jsons]
        self.common_keys = set.intersection(*self.key_sets)
        self.all_keys = set.union(*self.key_sets)
        self.used_keys = self.find_all_used_keys(self.all_keys)
        self.missing_keys = self.used_keys - self.common_keys
        self.unused_keys = self.all_keys - self.used_keys

    def find_all_used_keys(self, expected_keys=[]) -> set:
        # Note that this will only find string literals passed to the translate() or sendTranslatedMessage() methods!
        # String variables passed to them can be checked against expected_keys
        used = set()
        potential = set()
        for root, dirs, files in os.walk(SOURCE_FOLDER):
            for file in files:
                if file.rpartition('.')[-1] in SOURCE_EXTENSIONS:
                    filename = os.path.join(root, file)
                    with open(filename, 'r') as f:
                        data = f.read()  # Loads in entire file at once
                        for k in self.TRANSLATION_KEY.findall(data):
                            used.add(k)
                        for k in self.POTENTIAL_KEY.findall(data):
                            potential.add(k)
                        for label in self.COMMAND_LABEL.findall(data):
                            used.add(f'commands.{label}.description')
        return used | (potential & expected_keys)

    def _lint_report_language(self, lang: str, keys: set, flattened: dict, primary_language_flattened: dict) -> None:
        missing = self.used_keys - keys
        unused = keys - self.used_keys
        identical_keys = set() if (lang == PRIMARY_LANGUAGE) else {key for key in keys if primary_language_flattened.get(key, None) == flattened.get(key)}
        placeholder_keys = {key for key in keys if flattened.get(key).startswith(PRIMARY_FALLBACK_PREFIX)}
        p1 = f'Language {lang} has {len(missing)} missing keys and {len(unused)} unused keys.'
        p2 = 'This is the primary language.' if (lang == PRIMARY_LANGUAGE) else f'{len(identical_keys)} match {PRIMARY_LANGUAGE}, {len(placeholder_keys)} have the placeholder mark.'
        print(f'{p1} {p2}')

        lint_categories = {
            'Missing': missing,
            'Unused': unused,
            f'Matches {PRIMARY_LANGUAGE}': identical_keys,
            'Placeholder': placeholder_keys,
        }
        for name, category in lint_categories.items():
            if len(category) > 0:
                print(name + ':')
                print(JsonHelpers.pprint_keys(sorted(category)))

    def lint_report(self) -> None:
        print(f'There are {len(self.missing_keys)} translation keys in use that are missing from one or more language files.')
        print(f'There are {len(self.unused_keys)} translation keys in language files that are not used.')
        primary_language_flattened = self.flattened_jsons[LANGUAGE_FILENAMES.index(PRIMARY_LANGUAGE)]
        for lang, keys, flattened in zip(LANGUAGE_FILENAMES, self.key_sets, self.flattened_jsons):
            print('')
            self._lint_report_language(lang, keys, flattened, primary_language_flattened)

    def rename_keys(self, key_remappings: dict) -> None:
        # Unfortunately we can't rename keys in-place preserving insertion order, so we have to make new dicts
        for i in range(len(self.flattened_jsons)):
            self.flattened_jsons[i] = {key_remappings.get(k,k):v for k,v in self.flattened_jsons[i].items()}

    def update_secondary_languages(self):
        # Push en_US fallback
        primary_language_json = self.language_jsons[LANGUAGE_FILENAMES.index(PRIMARY_LANGUAGE)]
        for filename, lang in zip(LANGUAGE_FILENAMES, self.language_jsons):
            if filename != PRIMARY_LANGUAGE:
                js = JsonHelpers.deep_clone_and_fill(primary_language_json, lang)
                JsonHelpers.save(LANGUAGE_FOLDER + filename, js)

    def update_all_languages_from_flattened(self):
        for filename, flat in zip(LANGUAGE_FILENAMES, self.flattened_jsons):
            JsonHelpers.save(LANGUAGE_FOLDER + filename, JsonHelpers.unflatten(flat))

    def save_flattened_languages(self, prefix='flat_'):
        for filename, flat in zip(LANGUAGE_FILENAMES, self.flattened_jsons):
            JsonHelpers.save(prefix + filename, flat)


class InteractiveRename(cmd.Cmd):
    intro = 'Welcome to the interactive rename shell.   Type help or ? to list commands.\n'
    prompt = '(rename) '
    file = None

    def __init__(self, language_manager: LanguageManager) -> None:
        super().__init__()
        self.language_manager = language_manager
        self.flat_keys = [key for key in language_manager.flattened_jsons[LANGUAGE_FILENAMES.index(PRIMARY_LANGUAGE)].keys()]
        self.mappings = {}

    def do_add(self, arg):
        '''
        Prepare to rename an existing translation key. Will not actually rename anything until you confirm all your pending changes with 'rename'.
        e.g. a single string:  add commands.execution.argument_error commands.generic.invalid.argument
        e.g. a group:          add commands.enter_dungeon commands.new_enter_dungeon
        '''
        args = arg.split()
        if len(args) < 2:
            self.do_help('add')
            return
        old, new = args[:2]
        if old in self.flat_keys:
            self.mappings[old] = new
        else:
            # Check if we are renaming a higher level
            if not old.endswith('.'):
                old = old + '.'
            results = [key for key in self.flat_keys if key.startswith(old)]
            if len(results) > 0:
                if not new.endswith('.'):
                    new = new + '.'
                new_mappings = {key: key.replace(old, new) for key in results}
                # Ask for confirmation
                print('Will add the following mappings:')
                ppprint(new_mappings)
                print('Add these mappings? [y/N]')
                if self.prompt_yn():
                    for k,v in new_mappings.items():
                        self.mappings[k] = v
            else:
                print('No translation keys matched!')
    
    def complete_add(self, text: str, line: str, begidx: int, endidx: int) -> list:
        if text == '':
            return [k for k in {key.partition('.')[0] for key in self.flat_keys}]
        results = [key for key in self.flat_keys if key.startswith(text)]
        if len(results) > 40:
            # Collapse categories
            if text[-1] != '.':
                text = text + '.'
            level = text.count('.') + 1
            new_results = {'.'.join(key.split('.')[:level]) for key in results}
            return list(new_results)
        return results

    def do_remove(self, arg):
        '''
        Remove a pending rename mapping. Takes the old name of the key, not the new one.
        e.g. a single key:  remove commands.execution.argument_error
        e.g. a group:       remove commands.enter_dungeon
        '''
        old = arg.split()[0]
        if old in self.mappings:
            self.mappings.pop(old)
        else:
            # Check if we are renaming a higher level
            if not old.endswith('.'):
                old = old + '.'
            results = [key for key in self.mappings if key.startswith(old)]
            if len(results) > 0:
                # Ask for confirmation
                print('Will remove the following pending mappings:')
                print(JsonHelpers.pprint_keys(results))
                print('Delete these mappings? [y/N]')
                if self.prompt_yn():
                    for key in results:
                        self.mappings.pop(key)
            else:
                print('No pending rename mappings matched!')
    
    def complete_remove(self, text: str, line: str, begidx: int, endidx: int) -> list:
        return [key for key in self.mappings if key.startswith(text)]

    def do_rename(self, _arg):
        'Applies pending renames and overwrites language jsons.'
        # Ask for confirmation
        print('Will perform the following mappings:')
        ppprint(self.mappings)
        print('Perform and save these rename mappings? [y/N]')
        if self.prompt_yn():
            self.language_manager.rename_keys(self.mappings)
            self.language_manager.update_all_languages_from_flattened()
            print('Renamed keys, closing')
            return True
        else:
            print('Do you instead wish to quit without saving? [yes/N]')
            if self.prompt_yn(True):
                print('Left rename shell without renaming')
                return True

    def prompt_yn(self, strict_yes=False):
        if strict_yes:
            return input('(yes/N) ').lower() == 'yes'
        return input('(y/N) ').lower()[0] == 'y'


def main(args: argparse.Namespace):
    # print(args)
    language_manager = LanguageManager()
    errors = None
    if args.lint_report:
        language_manager.lint_report()
        missing = language_manager.used_keys - language_manager.key_sets[LANGUAGE_FILENAMES.index(PRIMARY_LANGUAGE)]
        if len(missing) > 0:
            errors = f'[ERROR] {len(missing)} keys missing from primary language json!\n{JsonHelpers.pprint_keys(missing)}'
    if prefix := args.save_flattened:
        language_manager.save_flattened_languages(prefix)
    if args.update:
        print('Updating secondary languages')
        language_manager.update_secondary_languages()
    if args.interactive_rename:
        language_manager.load_jsons()  # Previous actions may have changed them on-disk
        try:
            InteractiveRename(language_manager).cmdloop()
        except KeyboardInterrupt:
            print('Left rename shell without renaming')
    if errors:
        print(errors)
        exit(1)



if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Manage Grasscutter's language json files.")
    parser.add_argument('-u', '--update', action='store_true',
        help=f'Update secondary language files to conform to the layout of the primary language file ({PRIMARY_LANGUAGE}) and contain any new keys from it.')
    parser.add_argument('-l', '--lint-report', action='store_true',
        help='Prints a lint report, listing unused, missing, and untranslated keys among all language jsons.')
    parser.add_argument('-f', '--save-flattened', const='./flat_', metavar='prefix', nargs='?',
        help='Save copies of all the language jsons in a flattened key form.')
    parser.add_argument('-i', '--interactive-rename', action='store_true',
        help='Enter interactive rename mode, in which you can specify keys in flattened form to be renamed.')
    args = parser.parse_args()
    main(args)