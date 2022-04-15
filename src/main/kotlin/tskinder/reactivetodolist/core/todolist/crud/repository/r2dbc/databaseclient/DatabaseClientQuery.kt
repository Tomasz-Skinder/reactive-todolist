package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.databaseclient

object DatabaseClientQuery {

    const val INSERT_TODOLIST =
        """INSERT INTO todolist (name) 
            VALUES (:name) 
            RETURNING id"""

    const val GET_TODOLIST_BY_ID =
        """SELECT id, name 
            FROM todolist 
            WHERE id=:id"""

    const val INSERT_ITEM =
        """INSERT INTO item(content, status, creation_date, deadline, todolist_id) 
            VALUES(:content, :status, :creationDate, :deadline, :todolistId)"""

    const val GET_ITEM_BY_ID =
        """SELECT id, content, status, creation_date, modification_date, deadline, todolist_id
            FROM item 
            WHERE id = :id AND todolist_id = :todolistId"""

    const val GET_ITEMS_BY_TODOLIST_ID =
        """SELECT id, content, status, creation_date, modification_date, deadline, todolist_id 
            FROM item
            WHERE todolist_id = :todolistId"""

    const val UPDATE_ITEM_CONTENT =
        """UPDATE item 
            SET content = :content, modification_date = :modificationDate, deadline = :deadline
            WHERE id = :id
            RETURNING id"""

    const val DELETE_ITEM =
        """DELETE 
            FROM item 
            WHERE id = :id AND todolist_id = :todolistId"""
}