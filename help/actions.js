
function runPatternLiveAction(pluginId, className, argument) {
	var encodedArg = encodeURIComponent(argument);
	var url = "livehelp/?pluginId=" + pluginId + "&class=" + className + 
		"&argument=" + encodedArg + "&sequence=" + Math.random();

	window.status = url; // This fires a status changed event..!
	
	// Has the event been handled?
	if (window.status.length > 0) {
		liveAction(pluginId, className, argument);
	}
}
