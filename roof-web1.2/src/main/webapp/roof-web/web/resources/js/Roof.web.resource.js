ROOF.Utils.ns('Roof.web.resource');

Roof.web.resource.createPath = function(parentPath, parentType, ident, type) {
	if (parentType == "Module") {
		parentPath = parentPath.replace("Action", "");
		if (type == "Module") {
			if (parentPath == "/") {
				return parentPath + ident + "Action";
			}
			return parentPath + "_" + ident + "Action";
		} else {
			return parentPath + "Action" + "!" + ident;
		}
	} else {
		return parentPath + "_" + ident;
	}
}

Roof.web.resource.createPattern = function(path, type) {
	path = path.replace("/", "/**/");
	if (type == "Module") {
		return path.replace("Action", "*");
	} else {
		return path + "*";
	}
}
