package shared


data class Forest<N, L>(
    val trees: List<Node<N, L>>
)

interface Node<N, L> {
    companion object {
        fun <N, L> of(value: N, children: Set<Node<N, L>>): InternalNode<N, L> = InternalNode(value, children)
        fun <N, L> of(value: L): Leaf<N, L> = Leaf(value)
    }

    fun nodes(): Sequence<Node<N, L>>
}

data class InternalNode<N, L>(
    val value: N,
    val children: Set<Node<N, L>>
) : Node<N, L> {
    override fun nodes() = children.asSequence() + this
}

data class Leaf<N, L>(
    val value: L
) : Node<N, L> {
    override fun nodes() = emptySequence<Node<N, L>>()
}