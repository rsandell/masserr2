import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Ability

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Abilities") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    script("admin = ${st.bind(my)}")
    script(src: "${resURL}/js/admin/abilities.js")

    legend(_("Abilities"))
    table(class: "table table-hover") {
        tr() {
            th(_("Id"))
            th(_("Type"))
            th(_("Name"))
            th(style: "text-align: right", _("Income"))
            th(_("Doc URL"))
            th() {st.nbsp()}
        }
        my.getAbilities().each() { Ability ability ->
            tr(ability: f.toNavId(ability.getId())) {
                td(width: "10%") {small(ability.getId())}
                td(width: "15%") {small(ability.getType().name())}
                td(width: "25%", ability.getName())
                td(width: "10%", style: "text-align: right", ability.getBaseMonthlyIncome())
                td(width: "30%", ability.getDocUrl())
                td(width: "10%") {
                    a(class: "btn", href: "javascript:editAbilitiesRow('${f.toNavId(ability.getId())}')"){
                        i(class: "icon-edit")
                    }
                }
            }
        }
    }
}