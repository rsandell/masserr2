import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.modules.AdminModule

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("MasserrAdmin") + " " + Masserr.getInstance().getAppName()) {
    AdminModule module = my

    div(class: "row") {
        div(class: "span3") {
            div(class: "well") {
                a(href: "config") {h3(_("Config"))}
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                a(href: "abilities") {h3(_("Abilities"))}
                p(_("Edit global Abilities list"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                a(href: "othertraits") {h3(_("Other Traits"))}
                p(_("Edit global Other Traits list"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                a(href: "disciplines") {h3(_("Disciplines"))}
                p(_("Edit global Disciplines list"))
            }
        }
    }
    div(class: "row") {
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
        div(class: "span3") {
            div(class: "well") {
                h3(_("Config"))
                p(_("Change global configuration properties"))
            }
        }
    }
}