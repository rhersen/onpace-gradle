package name.hersen.onpace

data class RecentRideTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double,
    val achievement_count: Double
)

data class RecentRunTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double,
    val achievement_count: Double
)

data class RecentSwimTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double,
    val achievement_count: Double
)

data class YtdRideTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double
)

data class YtdRunTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double
)

data class YtdSwimTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double
)

data class AllRideTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double
)

data class AllRunTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double
)

data class AllSwimTotals(
    val count: Double,
    val distance: Double,
    val moving_time: Double,
    val elapsed_time: Double,
    val elevation_gain: Double
)

data class ActivityStats(
    val biggest_ride_distance: Double,
    val biggest_climb_elevation_gain: Double,
    val recent_ride_totals: RecentRideTotals,
    val recent_run_totals: RecentRunTotals,
    val recent_swim_totals: RecentSwimTotals,
    val ytd_ride_totals: YtdRideTotals,
    val ytd_run_totals: YtdRunTotals,
    val ytd_swim_totals: YtdSwimTotals,
    val all_ride_totals: AllRideTotals,
    val all_run_totals: AllRunTotals,
    val all_swim_totals: AllSwimTotals
)
