package tskinder.reactivetodolist.core.todolist.crud.repository

const val INSERT_TODOLIST = """INSERT INTO todolist (name) VALUES ($1) RETURNING id, name"""

const val GET_TODOLIST_BY_ID = """SELECT id, name FROM todolist WHERE id=$1"""

const val GET_ITEMS_BY_TODOLIST_ID = """
    SELECT id, content, status, creation_date, modification_date, deadline, todolist_id
    FROM item
    WHERE todolist_id = $1
    """

const val INSERT_ITEM = """
    INSERT INTO item(content, status, creation_date, deadline, todolist_id) 
    VALUES($1, $2, $3, $4, $5) 
    RETURNING id, content, status, creation_date, modification_date, deadline, todolist_id
    """

const val DBC_GET_ITEM_BY_ID = """
    SELECT id, content, status, creation_date, modification_date, deadline, todolist_id
    FROM item
    WHERE id = :id AND todolist_id = :todolistId
    """

const val DBC_GET_TODOLIST_BY_ID = """
    SELECT id, content, status, creation_date, modification_date, deadline, todolist_id
    FROM item
    WHERE todolist_id = :todolistId
    """

const val DBC_INSERT_ITEM = """
    INSERT INTO item(content, status, creation_date, deadline, todolist_id) 
    VALUES(:content, :status, :creationDate, :deadline, :todolistId) 
    RETURNING id, content, status, creation_date, modification_date, deadline, todolist_id
    """

const val DBC_DELETE_ITEM = """DELETE FROM item WHERE id = :id AND todolist_id = :todolistId"""