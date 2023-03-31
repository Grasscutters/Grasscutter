#!/usr/bin/env bash

# Grasscutter install script for GNU/Linux - Simpler version
# This installer doesn't ask you to install dependencies, you have to install them manually
# Made by TurtleIdiot and modified by syktyvkar (and then again modified by Blue)

# Stops the installer if any command has a non-zero exit status
set -e

# Checks for root
if [ $EUID != 0 ]; then
    echo "Please run the installer as root (sudo)!"
    exit
fi

is_command() {
    # Checks if given command is available
    local check_command="$1"
    command -v "${check_command}" > /dev/null 2>&1
}

# IP validation
valid_ip() {
    local ip=$1
    local stat=1

    if [[ $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
        OIFS=$IFS
        IFS="."
        ip=($ip)
        IFS=$OIFS
        [[ ${ip[0]} -le 255 && ${ip[1]} -le 255 \
            && ${ip[2]} -le 255 && ${ip[3]} -le 255 ]]
        stat=$?
    fi
    return $stat
}
echo "#################################"
echo ""
echo "This script will take for granted that you have all dependencies installed (mongodb, openjdk-17-jre/jre17-openjdk, wget, openssl, unzip, git, curl, base-devel), in fact, this script is recommended to update your current server installation, and it should run from the same folder as grasscutter.jar"
echo "#################################"
echo ""
echo "If you are using version > 2.8 of the client, make sure to use the patched metadata if you don't use Cultivation."
echo "Search for METADATA here: https://discord.gg/grasscutter."
echo ""
echo "#################################"
echo "You can find plugins here: https://discord.com/channels/965284035985305680/970830969919664218"
echo ""
echo "Grasscutter will be installed to script's running directory"
echo "Do you wish to proceed and install Grasscutter?"
select yn in "Yes" "No" ; do
    case $yn in
        Yes ) break;;
        No )
			echo "Aborting..."
			exit;;
    esac
done

if [ -d "./resources" ]
then
    echo "It's recommended to remove resources folder"
    echo "Remove resources folder?"
    select yn in "Yes" "No" ; do
        case $yn in
            Yes ) 
                rm -rf resources
                break;;
            No )
                echo "Aborting..."
                exit;;
        esac
    done
echo "You may need to remove data folder and config.json to apply some updates"
echo "#################################"
fi



# Allows choice between stable and dev branch
echo "Please select the branch you wish to install"
echo -e "!!NOTE!!: stable is the recommended branch.\nDo *NOT* use development unless you have a reason to and know what you're doing"
select branch in "stable" "development" ; do
    case $branch in
        stable )
            break;;
        development )
            break;;
    esac
done

echo -e "Using $branch branch for installing server \n"

# Prompt IP address for config.json and for generating new keystore.p12 file
echo "Please enter the IP address that will be used to connect to the server"
echo "This can be a local or a public IP address"
echo "This IP address will be used to generate SSL certificates, so it is important it is correct!"

while : ; do
    read -p "Enter server IP: " SERVER_IP
    if valid_ip $SERVER_IP; then
        break;
    else
        echo "Invalid IP address. Try again."
    fi
done

echo "Beginning Grasscutter installation..."


# Download resources
echo "Downloading Grasscutter BinOutputs..."
git clone --single-branch https://github.com/Koko-boya/Grasscutter_Resources.git Grasscutter-bins
mv ./Grasscutter-bins/Resources ./resources
rm -rf Grasscutter-bins # takes ~350M of drive space after moving BinOutputs... :sob:

# Download and build jar
echo "Downloading Grasscutter source code..."
git clone --single-branch -b $branch https://github.com/Grasscutters/Grasscutter.git Grasscutter-src #change this to download a fork

echo "Building grasscutter.jar..."
cd Grasscutter-src
chmod +x ./gradlew #just in case
./gradlew --no-daemon jar
mv $(find -name "grasscutter*.jar" -type f) ../grasscutter.jar
echo "Building grasscutter.jar done!"
cd ..

# Generate handbook/config
echo "Grasscutter will be started to generate data files"
java -jar grasscutter.jar -version

# Replaces "127.0.0.1" with given IP
echo "Replacing IP address in server config..."
sed -i "s/127.0.0.1/$SERVER_IP/g" config.json
# Generates new keystore.p12 with the server's IP address
# This is done to prevent a "Connection Timed Out" error from appearing
#	   after clicking to enter the door in the main menu/title screen
# This issue only exists when connecting to a server *other* than localhost
#	   since the default keystore.p12 has only been made for localhost

mkdir certs
cd certs
echo "Generating CA key and certificate pair..."
openssl req -x509 -nodes -days 25202 -newkey rsa:2048 -subj "/C=GB/ST=Essex/L=London/O=Grasscutters/OU=Grasscutters/CN=$SERVER_IP" -keyout CAkey.key -out CAcert.crt

echo "Generating SSL key and certificate pair..."
openssl genpkey -out ssl.key -algorithm rsa

# Creates a conf file in order to generate a csr
cat > csr.conf <<EOF
[ req ]
default_bits = 2048
prompt = no
default_md = sha256
req_extensions = req_ext
distinguished_name = dn

[ dn ]
C = GB
ST = Essex
L = London
O = Grasscutters
OU = Grasscutters
CN = $SERVER_IP

[ req_ext ]
subjectAltName = @alt_names

[ alt_names ]
IP.1 = $SERVER_IP
EOF

# Creates csr using key and conf
openssl req -new -key ssl.key -out ssl.csr -config csr.conf

# Creates conf to finalise creation of certificate
cat > cert.conf <<EOF

authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, keyAgreement, dataEncipherment
subjectAltName = @alt_names

[alt_names]
IP.1 = $SERVER_IP

EOF

# Creates SSL cert
openssl x509 -req -in ssl.csr -CA CAcert.crt -CAkey CAkey.key -CAcreateserial -out ssl.crt -days 25202 -sha256 -extfile cert.conf

echo "Generating keystore.p12 from key and certificate..."
openssl pkcs12 -export -out keystore.p12 -inkey ssl.key -in ssl.crt -certfile CAcert.crt -passout pass:123456

cd ../
mv ./certs/keystore.p12 ./keystore.p12
echo "Done!"

# Running scripts as sudo makes all Grasscutter files to be owned by root
# which may cause problems editing .jsons...
if [ $SUDO_USER ]; then
	echo "Changing Grasscutter files owner to current user..."
	chown -R $SUDO_USER:$SUDO_USER ./*
fi

echo "Removing unnecessary files..."
rm -rf ./certs ./Grasscutter-src

echo "All done!"
echo "-=-=-=-=-=--- !! IMPORTANT !! ---=-=-=-=-=-"
echo "Please make sure that ports 80, 443, 8888 and 22102 are OPEN (both tcp and udp)"
echo "In order to run the server, run the following command:"
echo "    sudo java -jar grasscutter.jar"
echo "The GM Handbook of all supported languages will be generated automatically when you start the server for the first time."
echo "You must run it using sudo as port 443 is a privileged port"
echo "To play, use the IP you provided earlier ($SERVER_IP) via GrassClipper or Fiddler"
echo ""

exit
