class BinaryTreeNode<K, V> (key: K,value: V): TreeNode<K, V, BinaryTreeNode<K, V>> (key, value) {
}

class BinaryTreeSearch<K : Comparable<K>, V>: TreeSearch<K, V, BinaryTreeNode<K, V>>() {
    override fun insert(key: K, value: V) {
        root = insert(root, key, value)
    }
    private fun insert(node: BinaryTreeNode<K, V>?, key: K, value: V): BinaryTreeNode<K, V> {
        //when a place for insertion is found, a node with the given key and value is returned
        if (node == null) return BinaryTreeNode(key, value)
        //Exceptions when inserting an existing key
        if (node.key == key && node.value == value) {
            throw IllegalArgumentException("The key: $key and value: $value already exists in the tree.")
        }
        if (node.key == key) {
            throw IllegalArgumentException("The key: $key already exists in the tree.")
        }
        //search a place to insert the key
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
        //Exception when deleting a non-existent key
        if (node == null) {
            throw NoSuchElementException("The key: $key was not found in the tree.")
        }
        //search for the key to be deleted
        else if (node.key < key) {
            node.rightChild = remove(node.rightChild, key)
        }
        else if (node.key > key) {
            node.leftChild = remove(node.leftChild, key)
        }
        else if (node.key == key) { //the key to be deleted was found
            /** if the node to be deleted does not have at least 1 child,
             * then replace the node with an existing child or null */
            val nodeLeft = node.leftChild
            val nodeRight = node.rightChild
            if (nodeLeft == null || nodeRight == null)
              return nodeLeft ?: nodeRight
            else {
              /** if the node to be deleted has 2 children,
               * then we look for the leftmost child of the right subtree
               * and put it instead of the node to be deleted, and delete the node */
              val nodeTemp = minNode(nodeRight)
              node.key = nodeTemp.key
              node.value = nodeTemp.value
              node.rightChild = remove(nodeRight, node.key)
            }
        }
        return node
    }
}