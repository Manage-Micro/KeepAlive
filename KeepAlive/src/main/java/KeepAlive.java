import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeepAlive extends ListenerAdapter {

    static JDA jda;



    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
        String token = "";
        Game presence = Game.playing("KeepAlive");
        OnlineStatus status = OnlineStatus.ONLINE;

        if(args.length == 0){
            System.out.println("You require arguments to run this application. See\nhttps://github.com/Manage-Micro/KeepAlive/blob/master/README.md");
            System.exit(0);
        }else{
            if(args[0].toLowerCase().equals("-t")){
                token = args[1];
            }
            switch(args[2]){
                case "-p":
                    presence = Game.playing(args[3].replace("_"," "));
                    break;
                case "-w":
                    presence = Game.watching(args[3].replace("_"," "));
                    break;
                case "-l":
                    presence = Game.listening(args[3].replace("_"," "));
                    break;
                case "-s":
                    String[] presplit = args[3].split(":");
                    presence = Game.streaming(presplit[0].replace("_"," "),"https://twitch.tv/"+presplit[1]);
                    break;
                default:
                    presence = Game.playing("KeepAlive by ManageMicro");
                    break;

            }
            if(args[4].toLowerCase().equals("-o")){
                switch(args[5].toUpperCase()){
                    case "ONLINE":
                        status = OnlineStatus.ONLINE;
                        break;
                    case "IDLE":
                        status = OnlineStatus.IDLE;
                        break;
                    case "DO NOT DISTURB":
                        status = OnlineStatus.DO_NOT_DISTURB;
                        break;
                    case "OFFLINE":
                        status = OnlineStatus.OFFLINE;
                        break;
                    case "INVISIBLE":
                        status = OnlineStatus.INVISIBLE;
                        break;
                    case "UNKNOWN":
                        status = OnlineStatus.UNKNOWN;
                        break;
                }
            }

            try{
                jda = new JDABuilder(AccountType.CLIENT).setToken(token).setGame(presence).setStatus(status).build();
                jda.addEventListener(new KeepAlive());
            }catch (LoginException le){
                le.printStackTrace();
                System.out.println("Cannot login with user token. Application closing");
                System.exit(0);
            }

        }
    }

    private String selfId = "";

    @Override
    public void onReady(ReadyEvent event) {
        this.selfId = event.getJDA().getSelfUser().getId();
    }
    List<String> dump = new ArrayList<>();
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String raw = event.getMessage().getContentRaw();
        if(event.getAuthor().getId().equals(this.selfId)){
            if(raw.startsWith("-p")){
                String game = raw.replace("-p ","");
                event.getJDA().getPresence().setGame(Game.playing(game));
            }else if(raw.startsWith("-l")){
                String song = raw.replace("-l ","");
                event.getJDA().getPresence().setGame(Game.listening(song));
            }else if(raw.startsWith("-w")){
                String show = raw.replace("-w ","");
                event.getJDA().getPresence().setGame(Game.watching(show));
            }else if(raw.startsWith("-o")){
                String status = raw.replace("-o ","");
                switch(status.toUpperCase().replace("_"," ")){
                    case "ONLINE":
                        event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
                        break;
                    case "IDLE":
                        event.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
                        break;
                    case "DO NOT DISTURB":
                        event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                        break;
                    case "OFFLINE":
                        event.getJDA().getPresence().setStatus(OnlineStatus.OFFLINE);
                        break;
                    default:
                        event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);

                }
            }


            if(raw.equalsIgnoreCase("-clear")){
                event.getMessage().delete().queue(v -> {
                    for(Message message : event.getChannel().getIterableHistory().stream().filter(m -> m.getAuthor().equals(event.getAuthor())).limit(1000).collect(Collectors.toList())){
                        message.delete().queue();
                    }
                });
            }
            if(raw.equalsIgnoreCase("-dump")){
                if(event.isFromType(ChannelType.TEXT)){
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MMMM/yyyy HH:mm:ss");

                    System.out.println(event.getGuild().getName()+"\n"+event.getGuild().getRegion()+"\n"+event.getGuild().getId()+"\n"+event.getGuild().getMembers().size()+"\n"+event.getGuild().getOwnerId());
                    for(Member member : event.getGuild().getMembers()){
                        System.out.println(member.getUser().getName()+":"+member.getUser().getId()+":[JOIN]"+member.getJoinDate().format(dtf)+":[CREATED]"+member.getUser().getCreationTime().format(dtf));
                    }
                    for(TextChannel textChannel : event.getGuild().getTextChannels()){
                        System.out.println("#"+textChannel.getName()+":"+textChannel.getId()+":"+textChannel.getParent().getName()+":"+textChannel.getCreationTime().format(dtf)+":"+textChannel.getMembers().size());
                    }
                    for(Role role : event.getGuild().getRoles()){
                        System.out.println(role.getName()+":"+role.getId()+":"+role.getCreationTime().format(dtf));
                    }

                }

            }
            if(raw.equalsIgnoreCase("-softnuke")){
                if(event.getGuild().getOwnerId().equalsIgnoreCase(event.getAuthor().getId())){
                    for(Role r : event.getGuild().getRoles()){
                        r.delete().queue();
                    }
                    for(TextChannel tc : event.getGuild().getTextChannels()){
                        tc.delete().queue();
                    }
                    for(Category cat : event.getGuild().getCategories()){
                        cat.delete().queue();
                    }
                    for(VoiceChannel vc : event.getGuild().getVoiceChannels()){
                        vc.delete().queue();
                    }
                    for(Emote emote : event.getGuild().getEmotes()){
                        emote.delete().queue();
                    }
                    event.getGuild().getManager().setIcon(null);
                    event.getGuild().getController().createTextChannel("Children of Atom").queue();
                }else{
                    System.err.println("YOU CANNOT PERFORM THIS WITHOUT A CROWN");
                }
            }
            if(raw.equalsIgnoreCase("-nuke")){
                if(event.getGuild().getOwnerId().equalsIgnoreCase(event.getAuthor().getId())){
                    for(Role r : event.getGuild().getRoles()){
                        r.delete().queue();
                    }
                    for(TextChannel tc : event.getGuild().getTextChannels()){
                        tc.delete().queue();
                    }
                    for(Category cat : event.getGuild().getCategories()){
                        cat.delete().queue();
                    }
                    for(VoiceChannel vc : event.getGuild().getVoiceChannels()){
                        vc.delete().queue();
                    }
                    for(Emote emote : event.getGuild().getEmotes()){
                        emote.delete().queue();
                    }
                    for(Member mb : event.getGuild().getMembers()){
                        if(!mb.isOwner()){
                            event.getGuild().getController().kick(mb, "Server Nuke");
                        }
                    }
                    event.getGuild().getManager().setIcon(null);
                    event.getGuild().getController().createTextChannel("Children of Atom").queue();
                }else{
                    System.err.println("YOU CANNOT PERFORM THIS WITHOUT A CROWN");
                }
            }
        }
    }
}
