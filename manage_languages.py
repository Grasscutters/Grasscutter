# Written for Python 3.6+
# Older versions don't retain insertion order of regular dicts
import json
import os
import re

US = 'en-US.json'
LANGUAGE_FOLDER = 'src/main/resources/languages/'
LANGUAGE_FILENAMES = os.listdir(LANGUAGE_FOLDER)
INDENT = 2
TRANSLATION_KEY = re.compile(r'[Tt]ranslate.*"(\w+\.[\w\.]+)"')
POTENTIAL_KEY = re.compile(r'"(\w+\.[\w\.]+)"')
ENGLISH_PREFIX = '🇺🇸'

def load_json(filename: str) -> dict:
    with open(filename, 'r') as file:
        return json.load(file)

def save_json(data: dict, filename: str) -> None:
    with open(filename, 'w', encoding='utf-8') as file:
        json.dump(data, file, ensure_ascii=False, indent=INDENT)

def flatten_json(data: dict, prefix='') -> dict:
    output = {}
    for key, value in data.items():
        if isinstance(value, dict):
            for k,v in flatten_json(value, f'{prefix}{key}.').items():
                output[k] = v
        else:
            output[f'{prefix}{key}'] = value
    return output

def unflatten_keys(keys) -> dict:
    output = {}
    def add_key(k: list, d: dict):
        if len(k) == 1:
            d[k[0]] = ''
        else:
            d[k[0]] = d.get(k[0], {})
            add_key(k[1:], d[k[0]])
    for key in keys:
        add_key(key.split('.'), output)
    return output

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

def find_all_used_keys(expected_keys=[]) -> set:
    # Note that this will only find string literals passed to the translate() or sendTranslatedMessage() methods!
    # String variables passed to them can be checked against expected_keys
    used = set()
    potential = set()
    for root, dirs, files in os.walk('src/'):
        for file in files:
            if file.endswith('.java'):
                filename = os.path.join(root, file)
                with open(filename, 'r') as f:
                    #for line in f:  # Doesn't load in entire file at once
                    for line in f.readlines():  # Loads in entire file at once
                        for k in TRANSLATION_KEY.findall(line):
                            used.add(k)
                        for k in POTENTIAL_KEY.findall(line):
                            potential.add(k)
    return used | (potential & expected_keys)

def lint_report(all_keys: set, common_keys: set, used_keys: set, key_sets: list, flattened_language_files: list) -> None:
    missing_keys = used_keys - common_keys
    unused_keys = all_keys - used_keys
    print(f'There are {len(missing_keys)} translation keys in use that are missing from one or more language files.')
    print(f'There are {len(unused_keys)} translation keys in language files that are not used.')

    primary_language = flattened_language_files[LANGUAGE_FILENAMES.index(US)]
    for lang, keys, flattened in zip(LANGUAGE_FILENAMES, key_sets, flattened_language_files):
        print('')
        missing = used_keys - keys
        unused = keys - used_keys
        identical_keys = set() if (lang == US) else {key for key in keys if primary_language.get(key, None) == flattened.get(key)}
        placeholder_keys = {key for key in keys if flattened.get(key).startswith(ENGLISH_PREFIX)}
        p1 = f'Language {lang} has {len(missing)} missing keys and {len(unused)} unused keys.'
        p2 = 'This is the primary language.' if (lang == US) else f'{len(identical_keys)} match en_US, {len(placeholder_keys)} have the placeholder mark.'
        print(f'{p1} {p2}')
        if len(missing) > 0:
            print('Missing:')
            print(pprint_keys(sorted(missing)))
        if len(unused) > 0:
            print('Unused:')
            print(pprint_keys(sorted(unused)))
        if len(identical_keys) > 0:
            print('Matches English:')
            print(pprint_keys(sorted(identical_keys)))

def deep_clone_and_fill(d1: dict, d2: dict, fallback_prefix=ENGLISH_PREFIX) -> dict:
    out = {}
    for key, value in d1.items():
        if isinstance(value, dict):
            out[key] = deep_clone_and_fill(value, d2.get(key, {}), fallback_prefix)
        else:
            v2 = d2.get(key, value)
            if type(value) == str and v2 == value:
                out[key] = fallback_prefix + value
            else:
                out[key] = v2
    return out

def update_secondary_languages(primary_language=US):
    # Push en_US fallback
    primary_language_json = language_jsons[LANGUAGE_FILENAMES.index(primary_language)]
    new_language_files = [deep_clone_and_fill(primary_language_json, l) for l in language_jsons]
    for filename, js in zip(LANGUAGE_FILENAMES, new_language_files):
        if filename != primary_language:
            save_json(js, LANGUAGE_FOLDER + filename)


language_jsons = [load_json(LANGUAGE_FOLDER + filename) for filename in LANGUAGE_FILENAMES]
flattened_jsons = [flatten_json(j) for j in language_jsons]
key_sets = [set(j.keys()) for j in flattened_jsons]
common_keys = set.intersection(*key_sets)
all_keys = set.union(*key_sets)
used_keys = find_all_used_keys(all_keys)

lint_report(all_keys, common_keys, used_keys, key_sets, flattened_jsons)
update_secondary_languages()