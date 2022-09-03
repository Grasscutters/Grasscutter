import re
import subprocess


UPSTREAM = 'https://github.com/Grasscutters/Grasscutter.git'
RATCHET = 'LintRatchet'
RATCHET_FALLBACK = 'c517b8a2c95473811eb07e12e73c4a69e59fbbdc'


re_leading_whitespace = re.compile(r'^[ \t]+', re.MULTILINE)  # Replace with \1.replace('\t', '    ')
re_trailing_whitespace = re.compile(r'[ \t]+$', re.MULTILINE)  # Replace with ''
# Replace 'for (foo){bar' with 'for (foo) {bar'
re_bracket_space = re.compile(r'\) *\{(?!\})')  # Replace with ') {'
# Replace 'for(foo)' with 'foo (bar)'
re_keyword_space = re.compile(r'(?<=\b)(if|for|while|switch|try|else|catch|finally|synchronized) *(?=[\(\{])')  # Replace with '\1 '


def get_changed_filelist():
    # subprocess.run(['git', 'fetch', UPSTREAM, f'{RATCHET}:{RATCHET}'])  # Ensure LintRatchet ref is matched to upstream
    # result = subprocess.run(['git', 'diff', RATCHET, '--name-only'], capture_output=True, text=True)
    # if result.returncode != 0:
        # print(f'{RATCHET} not found, trying fallback {RATCHET_FALLBACK}')
    print(f'Attempting to diff against {RATCHET_FALLBACK}')
    result = subprocess.run(['git', 'diff', RATCHET_FALLBACK, '--name-only'], capture_output=True, text=True)
    if result.returncode != 0:
        # print('Fallback is also missing, aborting.')
        print(f'Could not find {RATCHET_FALLBACK}, aborting.')
        exit(1)
    return result.stdout.strip().split('\n')


def format_string(data: str):
    data = re_leading_whitespace.sub(lambda m: m.group(0).replace('\t', '    '), data)
    data = re_trailing_whitespace.sub('', data)
    data = re_bracket_space.sub(') {', data)
    data = re_keyword_space.sub(r'\1 ', data)
    if not data.endswith('\n'):  # Enforce trailing \n
        data = data + '\n'
    return data


def format_file(filename: str) -> bool:
    try:
        with open(filename, 'r') as file:
            data = file.read()
        data = format_string(data)
        with open(filename, 'w') as file:
            file.write(data)
        return True
    except FileNotFoundError:
        print(f'File not found, probably deleted: {filename}')
        return False


def main():
    filelist = [f for f in get_changed_filelist() if f.endswith('.java') and not f.startswith('src/generated')]
    replaced = 0
    not_found = 0
    if not filelist:
        print('No changed files due for formatting!')
        return
    print('Changed files due for formatting: ', filelist)
    for file in filelist:
        if format_file(file):
            replaced += 1
        else:
            not_found += 1
    print(f'Format complete! {replaced} formatted, {not_found} missing.')


if __name__ == '__main__':
    main()
