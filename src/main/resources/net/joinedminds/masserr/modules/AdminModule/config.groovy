
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Morality
import net.joinedminds.masserr.model.mgm.Config

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Config") + " " + Masserr.getInstance().getAppName()) {
    Config config = my.getConfig()
    form(class: "form-horizontal", action: "configSubmit", method: "POST") {
        legend(_("Config"))
        div(class: "control-group") {
            label(class: "control-label", for: "inputAppName", _("App Name"))
            div(class: "controls") {
                input(type: "text", id: "inputAppName", name: "appName", value: config.getAppName(), placeholder: _("App Name"))
            }
        }
        div(class: "control-group") {
            label(class: "control-label", for: "inputDefaultMorality", _("Default Morality Path"))
            div(class: "controls") {
                select(id: "inputDefaultMorality", name: "defaultMorality") {
                    my.getMoralityPaths().each {Morality morality ->
                        if(morality.getId() == config.getDefaultMorality()?.getId()) {
                            option(value: morality.getId(), selected: true, morality.getName())
                        } else {
                            option(value: morality.getId(), morality.getName())
                        }
                    }
                }
            }
        }
        div(class: "control-group") {
            div(class: "controls") {
                button(type: "submit", class: "btn", _("Save"))
            }
        }
    }
}