import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Ability
import net.sf.json.JSONObject

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Abilities") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    //st.bind(value: my, var: 'admin')
    script() {
        raw("var abilities = [")
        my.getAbilities().each() { Ability ability ->
            raw("jQuery.parseJSON( '" + JSONObject.fromObject(new Ability(ability)).toString() + "' ), ")
        }
        raw("];\n")
        raw("var aTypes =[")
        Ability.Type.values().each { type ->
            raw("'"+type.name()+"',")
        }
        raw("];\n")
    }

    legend(_("Abilities"))
    table(class: "table table-hover", id:"abilitiesTable") {
        tr(class: "heading") {
            th(width: "10%", _("Id"))
            th(width: "15%", _("Type"))
            th(width: "25%", _("Name"))
            th(width: "10%", style: "text-align: right", _("Income"))
            th(width: "30%", _("Doc URL"))
            th(width: "10%") {
                button(class: "btn btn-mini", onclick: "newAbility()") {
                    i(class: "icon-plus")
                }
            }
        }
    }
    script(src: "${resURL}/js/admin/abilities.js")

}