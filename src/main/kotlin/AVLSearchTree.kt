/** use height for balance tree. The tree is self-balancing
 *  because the difference between the height of the left and right subtree is -1, 0 or +1.*/
class AVLTreeNode<K: Comparable<K>, V> (key: K, value: V) : TreeNode<K, V, AVLTreeNode<K, V>> (key, value) {
    var height = 1
}

class AVLTreeSearch<K : Comparable<K>, V> : TreeSearch<K, V, AVLTreeNode<K, V>>() {
    private fun insert(root: AVLTreeNode<K, V>?, key: K, value: V): AVLTreeNode<K, V> {
        /**walk through the tree until
         * we find the zero vertex where we will place the new element*/
        if (root == null) return AVLTreeNode(key, value)
        if (key < root.key) root.leftChild = insert(root.leftChild, key, value)
        else if (key > root.key) root.rightChild = insert(root.rightChild, key, value)
        else if (value != root.value) {
            throw IllegalArgumentException("The key: $key already exists in the tree.")
        } else {
            throw IllegalArgumentException("The key: $key and value: $value already exists in the tree.")
        }
        updateHeight(root)
        val balance = balanceFactor(root)
        if (balance > 1) {
            /**the advantage on the left,
             * depending on whether the lower vertex is on the left or on the right,
             * the balancing depends*/
            if (key > root.rightChild!!.key) {
                return rightRotate(root)
            } else {
                root.rightChild = leftRotate(root.rightChild!!)
                return rightRotate(root)
            }
        }
        if (balance < -1) {
            /**the advantage on the right*/
            if (key < root.leftChild!!.key) {
                return leftRotate(root)
            } else {
                root.leftChild = rightRotate(root.leftChild!!)
                return leftRotate(root)
            }
        }
        return root
    }

    override fun insert(key: K, value: V) {
        root = insert(root, key, value)
    }

    private fun height(root: AVLTreeNode<K, V>?): Int {
        /**if the vertex is empty, returns 0*/
        return root?.height ?: 0
    }

    private fun balanceFactor(root: AVLTreeNode<K, V>): Int {
        return height(root.rightChild) - height(root.leftChild) // key??
    }

    private fun updateHeight(root: AVLTreeNode<K, V>) {
        root.height = maxOf(height(root.leftChild), height(root.rightChild)) + 1
    }

    private fun leftRotate(root: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        val x = root.leftChild!!
        val t2 = x.rightChild
        x.rightChild = root
        root.leftChild = t2
        updateHeight(root)
        updateHeight(x)
        return x
    }

    private fun rightRotate(root: AVLTreeNode<K, V>): AVLTreeNode<K, V> {
        val y = root.rightChild!!
        val t2 = y.leftChild
        y.leftChild = root
        root.rightChild = t2
        updateHeight(root)
        updateHeight(y)
        return y
    }

    override fun remove(key: K) {
        root = remove(root, key)
    }

    private fun remove(root: AVLTreeNode<K, V>?, key: K): AVLTreeNode<K, V>? {
        if (root == null) {
            throw NoSuchElementException("The key: $key was not found in the tree.")
        }
        var newRoot: AVLTreeNode<K, V> = root
        if (key < newRoot.key) {
            newRoot.leftChild = remove(newRoot.leftChild, key)
        } else if (key > newRoot.key) {
            newRoot.rightChild = remove(newRoot.rightChild, key)
        } else {
            /** when we find a vertex,
             *  we check its children,
             *  if at least one child is zero,
             *  then we return either one of the children or zero*/
            if (newRoot.leftChild == null || newRoot.rightChild == null) {
                val temp = newRoot.leftChild ?: newRoot.rightChild
                if (temp == null) {
                    return null
                } else {
                    newRoot = temp
                }
            } else {
                /**After removal,
                 * we restructure the tree if necessary to maintain its balanced height.
                 * we took the minimum element from the right. vertex,
                 * assigned it to the vertex that we want to delete
                 * and delete the extra element that we found*/
                val temp = minNode(newRoot.rightChild!!)
                newRoot.key = temp.key
                newRoot.value = temp.value
                newRoot.rightChild = remove(newRoot.rightChild, temp.key)
            }
        }
        updateHeight(newRoot)
        val balance = balanceFactor(newRoot)
        if (balance > 1) {
            if (balanceFactor(newRoot.rightChild!!) >= 0) {
                return rightRotate(newRoot)
            } else {
                newRoot.rightChild = leftRotate(newRoot.rightChild!!)
                return rightRotate(newRoot)
            }
        }
        if (balance < -1) {
            if (balanceFactor(newRoot.leftChild!!) <= 0) {
                return leftRotate(newRoot)
            } else {
                newRoot.leftChild = rightRotate(newRoot.leftChild!!)
                return leftRotate(newRoot)
            }
        }
        return newRoot
    }
}
