class BinaryTreeNode<K, V>(key: K, value: V) : TreeNode<K, V, BinaryTreeNode<K, V>>(key, value) {}

class BinarySearchTree<K : Comparable<K>, V> : TreeSearch<K, V, BinaryTreeNode<K, V>>() {
  /**
   * Public method for inserting the specified key with the specified value into the tree.
   * calls a private method and updates the root of the tree
   */
  override fun insert(key: K, value: V) {
    root = insert(root, key, value)
  }

  /**
   * The method inserts the specified key and the specified value into the tree,
   * returns the root of the updated tree. Uses recursion
   */
  private fun insert(node: BinaryTreeNode<K, V>?, key: K, value: V): BinaryTreeNode<K, V> {
    //when a place for insertion is found, a node with the given key and value is returned
    if (node == null) return BinaryTreeNode(key, value)
    when {
      //Exceptions when inserting an existing key
      node.key == key && node.value == value -> throw IllegalArgumentException("The key: $key and value: $value already exists in the tree.")
      node.key == key -> throw IllegalArgumentException("The key: $key already exists in the tree.")
      key > node.key -> node.rightChild = insert(node.rightChild, key, value)
      else -> node.leftChild = insert(node.leftChild, key, value)
    }
    return node
  }

  /**
   * Public method to remove a specified key from a tree
   * calls a private method and updates the root of the tree
   */
  override fun remove(key: K) {
    root = remove(root, key)
  }

  /**
   * The method removes the specified key from the tree,
   * returns the root of the updated tree. Uses recursion
   */
  private fun remove(node: BinaryTreeNode<K, V>?, key: K): BinaryTreeNode<K, V>? {
    //Exception when deleting a non-existent key
    if (node == null) {
      throw NoSuchElementException("The key: $key was not found in the tree.")
    } else if (node.key < key) {
      node.rightChild = remove(node.rightChild, key)
    } else if (node.key > key) {
      node.leftChild = remove(node.leftChild, key)
    } else { //the key to be deleted was found
      /* if the node to be deleted does not have at least 1 child,
       * then replace the node with an existing child or null */
      val nodeLeft = node.leftChild
      val nodeRight = node.rightChild
      if (nodeLeft == null || nodeRight == null) return nodeLeft ?: nodeRight
      else {
        val nodeTemp = minNode(nodeRight)
        node.key = nodeTemp.key
        node.value = nodeTemp.value
        node.rightChild = remove(nodeRight, node.key)
      }
    }
    return node
  }
}
