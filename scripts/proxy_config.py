import os

# This can also be replaced with another IP address.
USE_SSL = True
REMOTE_HOST = "localhost"
REMOTE_PORT = 443

if os.getenv('MITM_REMOTE_HOST') != None:
    REMOTE_HOST = os.getenv('MITM_REMOTE_HOST')
if os.getenv('MITM_REMOTE_PORT') != None:
    REMOTE_PORT = int(os.getenv('MITM_REMOTE_PORT'))
if os.getenv('MITM_USE_SSL') != None:
    USE_SSL = bool(os.getenv('MITM_USE_SSL'))

print('MITM Remote Host: ' + REMOTE_HOST)
print('MITM Remote Port: ' + str(REMOTE_PORT))
print('MITM Use SSL ' + str(USE_SSL))
