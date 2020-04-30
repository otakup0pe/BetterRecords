package tech.feldman.betterrecords;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BetterRecordsConfig {

    public static class Client {

        public final ForgeConfigSpec.BooleanValue devMode;
        public final ForgeConfigSpec.IntValue downloadMax;
        public final ForgeConfigSpec.BooleanValue playWhileDownloading;
        public final ForgeConfigSpec.IntValue streamBuffer;
        public final ForgeConfigSpec.BooleanValue streamRadio;
        public final ForgeConfigSpec.IntValue flashMode;

        public Client(ForgeConfigSpec.Builder builder) {
            devMode = builder
                    .comment("Enable developer mode")
                    .define("devmode", true);
            downloadMax = builder
                    .comment("Max file size to download (in megabytes)")
                    .defineInRange("downloadmax", 10, 0, Integer.MAX_VALUE);
            playWhileDownloading = builder
                    .comment("Play songs while downloading", "For those with fast internet!")
                    .define("playwhiledownloading", false);
            streamBuffer = builder
                    .comment("Size of buffer for streaming songs.", "Only touch this if you know what that means.")
                    .defineInRange("streambuffer", 1024, 256, 2048);
            streamRadio = builder
                    .comment("Should radio be streamed?")
                    .define("streamradio", true);
            flashMode = builder
                    .comment("Intensity of lights")
                    .defineInRange("flashmode", -1, -1, 3);
        }
    }

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT = specPair.getLeft();
        CLIENT_SPEC = specPair.getRight();
    }

    public static class Common {

        public final ForgeConfigSpec.IntValue maxSpeakerRadius;
        public final ForgeConfigSpec.BooleanValue useRemoteLibraries;
        public final ForgeConfigSpec.BooleanValue loadDefaultLibraries;

        public Common(ForgeConfigSpec.Builder builder) {
            maxSpeakerRadius = builder
                    .comment("Maximum speaker radius")
                    .defineInRange("maxspeakerradius", -1, -1, 1000);
            useRemoteLibraries = builder
                    .comment("Should the mod download libraries from the internet or the server?")
                    .define("useremotelibraries", true);
            loadDefaultLibraries = builder
                    .comment("Should default libraries be loaded")
                    .define("loaddefaultlibraries", true);
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }
}
