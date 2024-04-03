interface TreeInterface<K : Comparable<K>, V> {
    fun search(key: K): V?
    fun insert(key: K, value: V)
    fun remove(key: K)
    fun getKeys(): List<K>
    fun getValues(): List<V>
    fun getMinKey(): K?
    fun getMaxKey(): K?
    fun insert(list: List<Pair<K, V>>)
    fun remove(list: List<K>)
    fun replaceValue(key: K, newValue: V)
}

abstract class TreeNode<K, V, N: TreeNode<K, V, N>> (var key: K, var value: V) {
    var leftChild: N? = null
    var rightChild: N? = null
}

abstract class TreeSearch<K : Comparable<K>, V, N: TreeNode<K, V, N>> : TreeInterface<K, V> {
    var root: N? = null
    override fun search(key: K): V? {
        return search(root, key)?.value
    }
    private fun search(root: N?, key: K): N? {
        if (root == null) {
            return null
        }
        if (key == root.key) {
            return root
        }
        if (key < root.key) {
            return search(root.leftChild, key)
        }
        return search(root.rightChild, key)
    }

    override fun getValues(): List<V> {
        val values = mutableListOf<V>()
        inOrderTraversal(root) { values.add(it.value) }
        return values
    }
    override fun getKeys(): List<K> {
        val keys = mutableListOf<K>()
        inOrderTraversal(root) { keys.add(it.key) }
        return keys
    }
    private fun inOrderTraversal(root: N?, action: (N) -> Unit) {
        if (root == null) return
        inOrderTraversal(root.leftChild, action)
        action(root)
        inOrderTraversal(root.rightChild, action)
    }

    override fun getMaxKey(): K? {
        return getMaxKey(root)
    }
    private fun getMaxKey(root: N?): K? {
        // maximal key is the rightmost key
        if (root == null) {
            return null
        }
        if (root.rightChild == null) {
            return root.key
        }
        return getMaxKey(root.rightChild)
    }

    override fun getMinKey(): K? {
        return getMinKey(root)
    }
    private fun getMinKey(root: N?): K? {
        // minimal key is the leftmost key
        if (root == null) {
            return null
        }
        if (root.leftChild == null) {
            return root.key
        }
        return getMinKey(root.leftChild)
    }

    protected fun minNode(node: N): N {
        // minimal node is the leftmost node
        var current = node
        while (current.leftChild != null) {
            current = current.leftChild!!
        }
        return current
    }

    override fun insert(list: List<Pair<K, V>>) {
        for (i in list) {
            insert(i.first, i.second)
        }
    }
    override fun remove(list: List<K>) {
        for (i in list) {
            remove(i)
        }
    }
    override fun replaceValue(key: K, newValue: V) {
        //if the key is in the tree, we look for it and change the value, otherwise we throw an exception
        val result = search(root, key) ?: throw NoSuchElementException("The key: $key was not found in the tree.")
        result.value = newValue
    }
}
