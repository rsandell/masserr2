import net.joinedminds.masserr.Functions
import net.joinedminds.masserr.Masserr
import net.joinedminds.masserr.model.Ability
import net.sf.json.JSONObject

def l = namespace(lib.LayoutTagLib)
st = namespace("jelly:stapler")

l.layout(title: _("Abilities") + " " + Masserr.getInstance().getAppName()) {
    Functions f = h;
    st.bind(value: my, var: 'admin')
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
    script(type: "template", id: "t_abilityRow") {
        tr(ability: "{{ navId }}") {
            td {
                small("{{ id }}")
            }
            td {
                small("{{ type }}")
            }
            td("{{ name }}")
            td(style: "text-align: right;", "{{ baseMonthlyIncome }}")
            td("{{ urlPart }}")
            td {
                a(class: "btn btn-mini",  href: "javascript:editAbilitiesRow('{{ navId }}')") {
                    i(class: 'icon-edit')
                }
            }
        }
    }
    script(type: "template", id: "t_abilityForm") {
        tr(ability: "{{ navId }}") {
            td {
                input(type: 'hidden', name: 'id', value: "{{ id }}") {
                    small("{{ id }}")
                }
            }
            td("{{ generatedTypesSelect }}")
            td {
                input(type: 'text', name: 'name', value: '{{ name }}', required: "true")
            }
            td {
                input(type: 'number', style: 'text-align: right', name: 'baseMonthlyIncome', value: '{{ baseMonthlyIncome }}')
            }
            td {
                input(type: 'url', name: 'docUrl', value: '{{ docUrl }}')
            }
            td {
                button(type: 'button', class: 'btn btn-mini btn-primary', onclick: "submitAbility('{{ navId }}')") {
                    i(class: 'icon-check icon-white')
                }
            }
        }
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