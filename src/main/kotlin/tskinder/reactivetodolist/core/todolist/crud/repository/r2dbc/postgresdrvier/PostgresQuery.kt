package tskinder.reactivetodolist.core.todolist.crud.repository.r2dbc.postgresdrvier

object PostgresQuery {

    const val INSERT_TODOLIST =
        """INSERT INTO todolist (name) 
            VALUES ($1) 
            RETURNING id"""

    const val GET_TODOLIST_BY_ID =
        """SELECT id, name 
            FROM todolist 
            WHERE id=$1"""

    const val INSERT_ITEM =
        """INSERT INTO item(content, status, creation_date, deadline, todolist_id) 
            VALUES($1, $2, $3, $4, $5) 
            RETURNING id"""

    const val GET_ITEM_BY_ID =
        """SELECT id, content, status, creation_date, modification_date, deadline, todolist_id
            FROM item
            WHERE id = $1 AND todolist_id = $2"""

    const val GET_ITEMS_BY_TODOLIST_ID =
        """SELECT id, content, status, creation_date, modification_date, deadline, todolist_id
            FROM item
            WHERE todolist_id = $1"""

    const val UPDATE_ITEM_CONTENT =
        """UPDATE item 
            SET content = $1, modification_date = $2, deadline = $3
            WHERE id = $4
            RETURNING id"""

    const val DELETE_ITEM_BY_ID =
        """DELETE 
            FROM item 
            WHERE id = $1 AND todolist_id = $2"""
}