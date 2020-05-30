package name.hersen.onpace

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.javalin.plugin.json.JavalinJackson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonTest {
    @Test
    fun should() {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        JavalinJackson.configure(mapper)
        val all = """{"biggest_ride_distance":23208.4,"biggest_climb_elevation_gain":103.20001,"recent_ride_totals":{"count":6,"distance":60904.40087890625,"moving_time":17379,"elapsed_time":21078,"elevation_gain":856.0,"achievement_count":5},"recent_run_totals":{"count":14,"distance":118781.60083007812,"moving_time":39947,"elapsed_time":41424,"elevation_gain":1622.6000061035156,"achievement_count":3},"recent_swim_totals":{"count":0,"distance":0.0,"moving_time":0,"elapsed_time":0,"elevation_gain":0.0,"achievement_count":0},"ytd_ride_totals":{"count":10,"distance":114585,"moving_time":29234,"elapsed_time":35191,"elevation_gain":1521},"ytd_run_totals":{"count":74,"distance":605806,"moving_time":207015,"elapsed_time":221418,"elevation_gain":7951},"ytd_swim_totals":{"count":0,"distance":0,"moving_time":0,"elapsed_time":0,"elevation_gain":0},"all_ride_totals":{"count":88,"distance":905202,"moving_time":275514,"elapsed_time":328287,"elevation_gain":9977},"all_run_totals":{"count":756,"distance":5662894,"moving_time":1966460,"elapsed_time":2212709,"elevation_gain":65142},"all_swim_totals":{"count":2,"distance":1025,"moving_time":1762,"elapsed_time":1788,"elevation_gain":0}}"""
        val jsonNode = mapper.readValue(all, RootObject::class.java)
        assertEquals(23208.4, jsonNode.biggest_ride_distance)
    }
}
