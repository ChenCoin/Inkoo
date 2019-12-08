package xyz.maoka.inko

import xyz.maoka.inkoo.InkooActivity
import xyz.maoka.inkoo.page

class MainActivity : InkooActivity() {
    override fun main() = page {
        !"122"

        +"百无聊赖的你，收到了一封非注明发件人的信件"

        -"拆开信封"{ openLetter() }
    }

    private fun openLetter() = page {
        +"尊敬的唐尼先生:"
        +"恕我冒昧，谨定于本周末，鄙人将于lindsay古堡举行宴会，诚邀唐尼先生参加。如果先生不介意的话也可带上几位挚友。"
        +"敬请光临"
        +"马格纳斯伯爵"
        -"重新翻看信封"{

        }
        -"直接将信封丢进垃圾桶"{

        }
    }
}
