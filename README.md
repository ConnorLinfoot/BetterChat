BetterChat
=====================

[![Build Status](http://ci.connorlinfoot.com:8080/buildStatus/icon?job=BetterChat)](http://ci.connorlinfoot.com:8080/job/BetterChat/)

BetterChat allows multiple custom chat channels as well as a staff chat.
This plugin is currently in development!


### Latest Release
You can download the latest "stable" release of BetterChat on Spigot: http://www.spigotmc.org/resources/betterchat.4372/


### Features
* Staff Chat - A way for staff to talk between themselves privately.
* Custom Chat Channels - Allow players to switch between channels where only players in that channel can see messages.
* On-the-fly Channels - Use a command to create a channel and instantly start using it without any reboot or performance impact.


### TODO
* ~~Custom Chat Channels~~
* ~~Staff Chat~~
* On-the-fly Channels
* Permission to access ALL channels - Good for staff.
* More per channel options
* Channel configuration GUI
* Custom commands per channel - /NAME \<message\>
* Many more planned features will be added.


### Commands
* /channel [channel name] - Switch what channel you are currently talking in. If you don't specify a channel name it will give you information on your current channel. Aliases are /switchchannel and /c.
* /staffchat \<message\> - Send a message to all staff. Aliases are /sc.


### Permissions
* betterchat.channel.channelname - Replacing channelname with the name of the channel will give you the required permission to join a channel if it is enabled in the config to use permissions.
* betterchat.staff - Access to staff chat, including staff chat commands.

If you have any ideas or suggestions please post them as an issue!