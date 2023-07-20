import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;

    public static void main(String[] args) {


        JDABuilder jdaBuilder = JDABuilder.createDefault(DiscordConstants.token);
        jdaBuilder.addEventListeners(new ImageGenerator(),new Help(),new PingPong(),new WeatherChecking());
        jdaBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT);

        try {
            jda = jdaBuilder.build();
            jda.awaitReady();
            jda.getPresence().setActivity(Activity.competing(" stealing \uD83E\uDD5D"));
            jda.updateCommands().addCommands(Commands.slash("img","Shows an image").addOption(OptionType.STRING,"sources","image source",true,true),
                    Commands.slash("help","Gets all commands"),
                    Commands.slash("ping","Gets ping"),
                    Commands.slash("weather","Gets current weather").addOption(OptionType.STRING,"countries","countries",true,true)).queue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
