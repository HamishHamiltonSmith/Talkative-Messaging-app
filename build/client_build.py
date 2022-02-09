#Run as root if build fails due to permissions

#Sets up directorys:
# /usr/share/Talkative + client/UserData
# /usr/share/Talkative + client/Src

import os

print("[+] Ready to set up client")
x = str(input("Press enter to begin: "))

#Create directorys
if not os.path.exists("/usr/share/Talkative"):
    os.system("mkdir /usr/share/Talkative")

os.system("mkdir /usr/share/Talkative/client/")
os.system("mkdir /usr/share/Talkative/client/UserData")

#Transfer files and src dir
os.system("touch /usr/share/Talkative/client/UserData/user.txt")
f = open("/usr/share/Talkative/client/UserData/user.txt",'w')
f.write("DefaultUser1")
f.close()

os.system("mv ~/Downloads/Talkative/Src /usr/share/Talkative/client")

