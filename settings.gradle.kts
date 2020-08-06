rootProject.name = "bialydunajec-backend"

include(
    "bialydunajec-ddd:application",
    "bialydunajec-ddd:presentation",
    "bialydunajec-ddd:domain",
    "bialydunajec-ddd:infrastructure",
    "bialydunajec-ddd:dto",
    "bialydunajec-news:application",
    "bialydunajec-news:presentation",
    "bialydunajec-news:domain",
    "bialydunajec-news:infrastructure",
    "bialydunajec-camp-edition:application",
    "bialydunajec-camp-edition:presentation",
    "bialydunajec-camp-edition:domain",
    "bialydunajec-camp-edition:infrastructure",
    "bialydunajec-camp-edition:messages",
    /*"bialydunajec-camp-schedule",
    "bialydunajec-camp-schedule:messages",
    "bialydunajec-camp-schedule:dto",
    "bialydunajec-camp-schedule:ddd-axon",*/
    "bialydunajec-academic-ministry:application",
    "bialydunajec-academic-ministry:presentation",
    "bialydunajec-academic-ministry:domain",
    "bialydunajec-academic-ministry:infrastructure",
    "bialydunajec-academic-ministry:messages",
    "bialydunajec-registrations:application",
    "bialydunajec-registrations:presentation",
    "bialydunajec-registrations:domain",
    "bialydunajec-registrations:infrastructure",
    "bialydunajec-registrations:messages",
    "bialydunajec-registrations:dto",
    "bialydunajec-registrations:read-model",
    "bialydunajec-email:application",
    "bialydunajec-email:presentation",
    "bialydunajec-email:domain",
    "bialydunajec-email:infrastructure",
    "bialydunajec-email:messages",
    "bialydunajec-email:read-model",
    "bialydunajec-users:application",
    "bialydunajec-users:presentation",
    "bialydunajec-users:domain",
    "bialydunajec-users:infrastructure",
    "bialydunajec-users:messages",
   /* "bialydunajec-camp-bus:application",
    "bialydunajec-camp-bus:presentation",
    "bialydunajec-camp-bus:domain",
    "bialydunajec-camp-bus:infrastructure",
    "bialydunajec-camp-bus:messages",*/
    "bialydunajec-rx-bus",
    "bialydunajec-gallery",
    "bialydunajec-gallery:application",
    "bialydunajec-gallery:presentation",
    "bialydunajec-gallery:domain",
    "bialydunajec-gallery:infrastructure",
    "bialydunajec-gallery:messages",
    /*"bialydunajec-faq",
    "bialydunajec-faq:application",
    "bialydunajec-faq:presentation",
    "bialydunajec-faq:domain",
    "bialydunajec-faq:infrastructure",*/
    "bialydunajec-authorization:server"
)
/*
Change all children projects names with following pattern: <project name>+"-"<module name>
For example: bialydunajec-core:application will be bialydunajec-core:bialydunajec-core-application
Reference: http://blog.lick-me.org/2014/11/gradle-gotcha-duplicate-project-names/
 */
rootProject.children.forEach { p ->
    p.children.forEach { module ->
        module.name = p.name + "-" + module.name
    }
}
