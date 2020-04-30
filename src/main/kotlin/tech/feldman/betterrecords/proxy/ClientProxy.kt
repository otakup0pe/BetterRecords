package tech.feldman.betterrecords.proxy

class ClientProxy : CommonProxy() {
    companion object {
        var encodings = mutableListOf(
                "audio/ogg", "application/ogg",
                "audio/mp3",
                "audio/mpeg", "audio/mpeg; charset=UTF-8",
                "application/octet-stream",
                "audio/wav", "audio/x-wav")
    }
}
