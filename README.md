# KeepAlive
A Selfbot for Discord, written in JDA

## DISCLAIMER
This is a **SELFBOT** and in accordance with Discord [Terms of Service](https://support.discordapp.com/hc/en-us/articles/115002192352-Automated-user-accounts-self-bots-), self bots are PROHIBITED. By using this application, you are revoking any liability from the Developer. The onus is on YOU to manage YOUR account.


## Usage

This application is a `command line` application, meaning it must be opened using either Command Prompt, Terminal, or other emulator of one or the other

The command line arguments MUST be in the correct order otherwise the application will NOT start correctly. All arguments are required.

`java -jar KeepAlive.jar -t TOKEN [-p PLAYING|-w WATCHING|-l LISTENING|-s STREAMING:URL] -o STATUS`

For example, to start the selfbot displaying me Playing Minecraft, and appearing as Idle, the arguments would be:

`-t TOKEN -p Minecraft -o Idle`

For the `-s` argument, a Title and Valid Stream Channel (Twitch) must be provided. For example, if I wanted to show it as "Streaming Halo Fortnite Crossover", and the channel being Twitch the arguments would be:

`-t TOKEN -s Halo_Fortnite_Crossover:twitch`
As you can see, spaces MUST be replaced with Underscores.

Future versions of this will have corresponding images

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
