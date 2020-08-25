package ru.fylmr.diploma.ui.chat

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ChatView : MvpView {
    fun addMessages(messages: List<Message>, clearField: Boolean)

}
