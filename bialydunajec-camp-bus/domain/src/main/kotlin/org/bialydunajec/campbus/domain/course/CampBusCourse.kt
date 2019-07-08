package org.bialydunajec.campbus.domain.course

sealed class CampBusCourse {

    private class Planned {

    }

    private class InProgress {

    }

    private class Completed {

    }

}


sealed class Command {

}

sealed class Event {

}
