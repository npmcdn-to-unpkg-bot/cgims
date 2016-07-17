var whereStr = "";
for (var i = 0; i < table.columns.length; i++) {
	var column = table.columns[i];
	if (values[column.name]) {
		if (whereStr) {
			whereStr += " and ";
		}
		whereStr += column.columnName + "='" + values[column.name] + "'";
	}
}
if (whereStr) {
	whereStr = " where " + whereStr;
}
var sql = "select * from " + table.tableName + whereStr + " limit "
		+ targetPage * pageSize + "," + pageSize + ";";