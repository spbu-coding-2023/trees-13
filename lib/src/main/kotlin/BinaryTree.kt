class BinaryTreeNode<K, V> (key: K,value: V): TreeNode<K, V, BinaryTreeNode<K, V>> (key, value) {
}

class BinaryTreeSearch<K : Comparable<K>, V>: TreeSearch<K, V, BinaryTreeNode<K, V>>() {
    override fun insert(key: K, value: V) {
        root = insert(root, key, value)
    }
    private fun insert(node: BinaryTreeNode<K, V>?, key: K, value: V): BinaryTreeNode<K, V> {
        if (node == null) return BinaryTreeNode<K, V> (key, value)
        if (node.key == key && node.value == value) {
            throw IllegalArgumentException("The key: $key and value: $value already exists in the tree.")
        }
        if (node.key == key) {
            throw IllegalArgumentException("The key: $key already exists in the tree.")
        }
        if (key > node.key) {
            node.rightChild = insert(node.rightChild, key, value)
        }
        else if (key < node.key) {
            node.leftChild = insert(node.leftChild, key, value)
        }
        return node
    }
    override fun remove(key: K) {
        root = remove(root, key)
    }
    private fun remove(node: BinaryTreeNode<K, V>?, key: K): BinaryTreeNode<K, V>? {
        if (node == null) {
            throw NoSuchElementException("The key: $key was not found in the tree.")
        }
        else if (node.key < key) {
            node.rightChild = remove(node.rightChild, key)
        }
        else if (node.key > key) {
            node.leftChild = remove(node.leftChild, key)
        }
        else if (node.key == key) {
            if (node.leftChild == null || node.rightChild == null) {
                if (node.leftChild != null){
                    return node.leftChild
                } else {
                    return node.rightChild
                }
            }
            if (node.leftChild != null && node.rightChild != null) {
                val temp =  minNode(node.rightChild!!)
                node.key = temp.key
                node.value = temp.value
                node.rightChild = remove(node.rightChild, node.key)
            }
        }
        return node
    }
}