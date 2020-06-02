package name.hersen.onpace

data class Authentication(
    val token_type: String,
    val expires_at: Number,
    val expires_in: Number,
    val refresh_token: String,
    val access_token: String,
    val athlete: Athlete
)

data class Athlete(
    val id: Number,
    val username: String,
    val resource_state: Number,
    val firstname: String,
    val lastname: String,
    val city: String,
    val state: String,
    val country: String,
    val sex: String,
    val premium: Boolean,
    val summit: Boolean,
    val created_at: String,
    val updated_at: String,
    val badge_type_id: Number,
    val profile_medium: String,
    val profile: String,
    val friend: String?,
    val follower: String?
)

