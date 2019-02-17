# KeepAlive
A Selfbot for Discord, written in JDA

## DISCLAIMER
This is a **SELFBOT** and in accordance with Discord [Terms of Service](https://support.discordapp.com/hc/en-us/articles/115002192352-Automated-user-accounts-self-bots-), self bots are PROHIBITED. By using this application, you are revoking any liability from the Developer. The onus is on YOU to manage YOUR account.


## Usage

This application is a `command line` application, meaning it must be opened using either Command Prompt, Terminal, or other emulator of one or the other.

**YOU MUST BE USING JRE 1.8 (JAVA 8) OR NEWER TO USE THIS APPLICATION**

The command line arguments MUST be in the correct order otherwise the application will NOT start correctly. All arguments are required.

`java -jar KeepAlive.jar -t TOKEN [-p PLAYING|-w WATCHING|-l LISTENING|-s STREAMING:URL] -o STATUS`

*Please note that accepted Arguments for status are Online, Idle, Do_Not_Disturb and Offline*

For example, to start the selfbot displaying me Playing Minecraft, and appearing as Idle, the arguments would be:

`-t TOKEN -p Minecraft -o Idle`

For the `-s` argument, a Title and Valid Stream Channel (Twitch) must be provided. For example, if I wanted to show it as "Streaming Halo Fortnite Crossover", and the channel being Twitch the arguments would be:

`-t TOKEN -s Halo_Fortnite_Crossover:twitch`
As you can see, spaces MUST be replaced with Underscores.

Future versions of this will have corresponding images

## Commands

In any channel where you have write access, you can use the following commands to edit your Presence (Playing, Listening, Watching) or Status.

`-p GAME`

  Example: `-p Minecraft`

`-l SONG`

  Example: `-l Viva la Vida - Coldplay`

`-w VIDEO`
  
  Example: `-w Rick Astley - Never Gonna Give You Up`

`-o STATUS`

  Example: `-o Idle`
  
The following commands can be used to perform various cleanup actions:

`-nuke`

  Requires you to be the owner of a server. Deletes everything, and creates a new channel called `Children of Atom`
  
`-dump`

  Creates a file called `dump-GUILD.LOG` to the working directory (where the JAR is) of all the info in a guild.

## FAQ

```diff
+ Where do I get my usertoken?
- Sorry, this bot requires basic knowledge of using self-bots. 
- Support will NOT be given in accessing your usertoken

+ Are my details safe?
- Yes, the application doesn't store, transmit or otherwise share your data 
- (outside of the Authentication process with the Discord API)

+ Is this legal?
- Legal? Yes. Ethical? No. Bannable? Most definitely

+ Can I request a feature?
- Sure.

```
