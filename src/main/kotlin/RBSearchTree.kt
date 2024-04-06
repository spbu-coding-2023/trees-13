
class RedBlackTreeNode<K, V> (key: K,value: V): TreeNode<K, V, RedBlackTreeNode<K, V>> (key, value) {
    var parent: RedBlackTreeNode<K, V>? = null
    var isRed: Boolean = false
}

class RedBlackTreeSearch<K : Comparable<K>, V>: TreeSearch<K, V, RedBlackTreeNode<K, V>>() {
    private fun rightRotate(node: RedBlackTreeNode<K, V>) {
        val temp: RedBlackTreeNode<K, V>? = node.leftChild //temp - old left child node
        /** instead of the left child of the node
         * we put the right child of the temp */
        node.leftChild = temp?.rightChild
        temp?.rightChild?.parent = node
        /** replace the node of the temp,
         * keeping its right child */
        temp?.parent = node.parent
        if (node.parent == null) {
            root = temp
        } else {
            if (node.parent?.rightChild == node) {
                node.parent?.rightChild = temp
            } else {
                node.parent?.leftChild = temp
            }
        }
        //set the node as the right child of the temp
        temp?.rightChild = node
        node.parent = temp
    }

    private fun leftRotate(node: RedBlackTreeNode<K, V>) {
        val temp: RedBlackTreeNode<K, V>? = node.rightChild //temp - old right child node
        /** instead of the right child of the node
         * we put the left child of the temp */
        node.rightChild = temp?.leftChild
        temp?.leftChild?.parent = node
        /** replace the node of the temp,
         * keeping its left child */
        temp?.parent = node.parent
        if (node.parent == null) {
            root = temp
        } else {
            if (node == node.parent?.leftChild) {
                node.parent?.leftChild = temp
            } else {
                node.parent?.rightChild = temp
            }
        }
        //set the node as the left child of the temp
        temp?.leftChild = node
        node.parent = temp
    }

    private fun balanceRemove(node: RedBlackTreeNode<K, V>) {
        val parent = node.parent ?: return
        val sibling = if (parent.leftChild == node) parent.rightChild else parent.leftChild
        /** always, when we call balancing, the node has a brother
         * that is not equal to null (otherwise the tree would not be balanced) */
        if (sibling == null) {
          throw RuntimeException("Tree invalid balanced")
        }
        if (sibling.isRed) { //if brother is red, change his color
          sibling.isRed = false
          parent.isRed = true
          leftRotate(parent)
        }
        else {
            //if brother is black and without child
            if (sibling.leftChild == null && sibling.rightChild == null) {
                //the node has a red parent, adjust the color
                if (parent.isRed) {
                    sibling.isRed = true
                    parent.isRed = false
                }
                //the node has a black non-root parent, adjust the color or left rotation
                else if (parent.parent != null) {
                    parent.isRed = true
                    leftRotate(parent)
                }
                //the node has a root parent, adjust the color
                else {
                    sibling.isRed = true
                }
            }
            /** if the brother’s right child is red,
             * the left one is any, then adjust the colors and make a left turn */
            else if (sibling.rightChild != null && sibling.rightChild!!.isRed) {
                sibling.isRed = sibling.parent!!.isRed
                sibling.rightChild!!.isRed = false
                parent.isRed = false
                leftRotate(parent)
            }
            /** if the brother's left child is red,
             * then adjust the colors and make a right turn and balance again */
            else if (sibling.leftChild != null && sibling.leftChild!!.isRed) {
                sibling.leftChild!!.isRed = false
                sibling.isRed = true
                rightRotate(sibling)
                balanceRemove(node)
            }
        }
    }

    override fun remove(key: K) {
        root = remove(root, key)
    }
    private fun remove(node: RedBlackTreeNode<K, V>?, key: K): RedBlackTreeNode<K, V>? {
        //if the key is not found, an exception is thrown
        if (node == null) {
            throw NoSuchElementException("The key: $key was not found in the tree.")
        }
        //is stored by the old parent to keep track of whether balancing has been done
        val oldParent = node.parent?.key
        //search for the key to be deleted
        if (key < node.key) {
            node.leftChild = remove(node.leftChild, key)
        }
        else if (key > node.key) {
            node.rightChild = remove(node.rightChild, key)
        }
        else {
            /** if the node being deleted has no children,
             * then if the node is red then we replace it with zero,
             * if it is black then we do balancing and replace the node with zero */
            if (node.leftChild == null && node.rightChild == null) {
                if (node.isRed) {
                    return null
                } else {
                    balanceRemove(node)
                    return null
                }
            }
            /** if one child is missing, replace the node
             * with this child and adjust the color */
            if (node.leftChild == null) {
                node.rightChild!!.isRed = false
                node.rightChild!!.parent = node.parent
                return node.rightChild
            }
            if (node.rightChild == null) {
                node.leftChild!!.isRed = false
                node.leftChild!!.parent = node.parent
                return node.leftChild
            }
            /** if the node to be deleted has 2 children,
             * then we look for the leftmost child of the right subtree
             * and put it instead of the node to be deleted, and delete the node */
            val temp = minNode(node.rightChild!!)
            node.key = temp.key
            node.value = temp.value
            node.rightChild = remove(node.rightChild, node.key)
        }
        //return the node, if there was balancing then we return the parent
        if (node.parent?.key != oldParent) {
            return node.parent
        }
        return node
    }

    /**
     * The method executes tree balancing
     * Gets the inserted node
     * Calling in the private insert method
     */
    private fun fixInsert( node1: RedBlackTreeNode<K,V> ) {
        var node : RedBlackTreeNode<K,V>? = node1
        var uncle : RedBlackTreeNode<K,V>?
         /*  If the parent is red, the uncle is red, the grandfather is black,
         *  then we do a vertex recoloring, which causes grandpa to change his color to red.
         *  The parent of the red grandfather can be red too,
         *  so we must check this case with a while loop
         */
        while ( node?.parent?.isRed == true ) {
            if ( node.parent?.parent?.rightChild == node.parent ) {
                uncle = node.parent?.parent?.leftChild
                /*
                 * The case of red parent, red uncle, black grandfather :
                 * repaint the parent and uncle in black, grandfather in red.
                 * Then we consider the grandfather as a node to make sure
                 * that its parent is not red (while loop)
                 */
                if ( uncle?.isRed != null && uncle.isRed ) {
                    uncle.isRed = false
                    node.parent?.isRed = false
                    node.parent?.parent?.isRed = true
                    node = node.parent?.parent
                }
                // The case of red parent, black uncle, black grandfather
                else { // If the node and its parent are not on the same side, then we move them to the same side using rotation
                    if ( node == node.parent?.leftChild ) {
                        node = node.parent
                        rightRotate(node!!)
                    }// then execute tree balancing
                    node.parent?.isRed = false
                    node.parent?.parent?.isRed = true
                    leftRotate(node.parent?.parent!!)
                }
            } else { // execute the same algorithm, but for the case when the uncle is the left child for the grandfather
                uncle  = node.parent?.parent?.rightChild
                if ( uncle?.isRed != null && uncle.isRed ) {
                    uncle.isRed = false
                    node.parent?.isRed = false
                    node.parent?.parent?.isRed = true
                    node = node.parent?.parent
                } else {
                    if ( node == node.parent?.rightChild ) {
                        node = node.parent
                        leftRotate(node!!)
                    }
                    node.parent?.isRed = false
                    node.parent?.parent?.isRed = true
                    rightRotate( node.parent?.parent!!)
                }
            }
            if (node == root) { // break the while loop, if the root is reached
                break
            }
        }
        root?.isRed = false // the root is always black
    }

    /**
     * The method returns the root value after inserting the node wiht following key and value parameters
     * @param K comparable type: key of the node to be inserted
     * @param V type: value of the node to be inserted
     */
    override fun insert(key: K, value: V) {
        root = insert(root, key, value)
        root?.isRed = false
    }

    /**
     * Searching the insertion place and considering the insertion of the root/children of the root
     * Return the value of the root after inserting
     */
    private fun insert(treeRoot: RedBlackTreeNode<K, V>?, key: K, value: V): RedBlackTreeNode<K, V>? {
        val node = RedBlackTreeNode(key, value)
        node.isRed = true
        var x: RedBlackTreeNode<K, V>? = treeRoot
        var y: RedBlackTreeNode<K, V>? = null
        while (x != null) { // searching the place to insert the node
            y = x
            x = if (key < x.key) {
                x.leftChild
            } else if (key > x.key) {
                x.rightChild
            } else if ( x.key == key && x.value == value) { // case of the existence of the node in the tree
                throw IllegalArgumentException("The key: $key and value: $value already exists in the tree.")
            } else {
                throw IllegalArgumentException("The key: $key already exists in the tree.")
            }
        }
        node.parent = y
        /*
         * the cases of filling the first and second
         * levels of the tree ( the root and its children )
         */
        if (y == null) {
            node.isRed = false
            root = node
            return root
        } else {
            if (node.key > y.key) {
                y.rightChild = node
            } else {
                y.leftChild = node
            }
            if (y.parent == null) {
                return root
            }
        }
        /*
         * if the place where we insert the node is not the root or its children,
         * then we execute balancing of the tree by fixInsert
         */
        fixInsert( node )
        return root
    }
}
