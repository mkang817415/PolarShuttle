package com.example.polarshuttle.data

import com.google.firebase.auth.FirebaseAuth

data class Request(
    var id: String = "",
    var requester: String = "",
    var userId: String = "",
    var numberOfRiders: Int = 0,
    var driver: String = "",
    var driverId: String = "",
    var date: String = "",
    var time: String = "",
    var pickUpLocation: ShuttleLocation?,
    var dropOffLocation: ShuttleLocation?,

    var status: Status = Status.PENDING,
){
    // No-argument constructor for Firebase
    constructor() : this("", "", "", 0, "", "",
        "", "", null, null, Status.PENDING)
}


enum class Status{
    PENDING, ACCEPTED, COMPLETED, CANCELED, INPROGRESS
}

enum class ShuttleLocation {
    _52HARPSWELL,
    BOODY_JOHNSON,
    BRUNS_APT,
    COFFIN_ST,
    COLES_CIRCLE,
    DRUCKENMILLER,
    EDWARDS_CENTER,
    FARLEY_FIELD_HOUSE,
    FORT_ANDROSS,
    GIBSON_HALL,
    HANNOFORDS,
    HELM_HOUSE,
    HARPSWELL_APT,
    HOWARD_HALL,
    HOWELL_HOUSE,
    JOSHUAS,
    J_AND_J_CLEANERS,
    LIGHT_HOUSE,
    MAC_HOUSE,
    MAYFLOWER_APT,
    POLAR_BEAR_STATUE,
    RHODES_HALL,
    BOC,
    PINE_APT,
    QUIBY_HOUSE,
    RED_HOUSE,
    SEADOG,
    SEARLES,
    SMITH_HOUSE,
    STOWE_INN,
    TRAIN_STATION,
    VAC,
    WALLGREENS;

    override fun toString(): String {
        return when (this) {
            _52HARPSWELL -> "52 Harpswell"
            BOODY_JOHNSON -> "Boody-Johnson"
            BRUNS_APT -> "Brunswick Apartments"
            COFFIN_ST -> "Coffin Street"
            COLES_CIRCLE -> "Coles Circle"
            DRUCKENMILLER -> "Druckenmiller Hall"
            EDWARDS_CENTER -> "Edwards Center"
            FARLEY_FIELD_HOUSE -> "Farley Field House"
            FORT_ANDROSS -> "Fort Andross"
            GIBSON_HALL -> "Gibson Hall"
            HANNOFORDS -> "Hannaford's"
            HELM_HOUSE -> "Helm House"
            HARPSWELL_APT -> "Harpswell Apartments"
            HOWARD_HALL -> "Howard Hall"
            HOWELL_HOUSE -> "Howell House"
            JOSHUAS -> "Joshua's Tavern"
            J_AND_J_CLEANERS -> "J & J Cleaners"
            LIGHT_HOUSE -> "The Lighthouse"
            MAC_HOUSE -> "Mac House"
            MAYFLOWER_APT -> "Mayflower Apartments"
            POLAR_BEAR_STATUE -> "Polar Bear Statue"
            RHODES_HALL -> "Rhodes Hall"
            BOC -> "Bowdoin Outing Club"
            PINE_APT -> "Pine Apartments"
            QUIBY_HOUSE -> "Quiby House"
            RED_HOUSE -> "Red House"
            SEADOG -> "Sea Dog Brewing"
            SEARLES -> "Searles Science Building"
            SMITH_HOUSE -> "Smith House"
            STOWE_INN -> "Stowe Inn"
            TRAIN_STATION -> "Train Station"
            VAC -> "Visual Arts Center"
            WALLGREENS -> "Walgreens"
        }
    }
}