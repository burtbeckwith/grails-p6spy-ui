modules = {

	'p6spy-ui' {

		dependsOn 'jquery'

		for (name in ['table', 'jquery.jdMenu', 'jquery.jdMenu.slate',
		              'tabs', 'tabs-accordion', 'p6spy-ui']) {
			resource url: [dir: 'css', file: name + '.css', plugin: 'p6spy-ui']
		}

		for (name in ['jquery.positionBy', 'jquery.bgiframe', 'jquery.jdMenu',
		              'jquery.dataTables.min', 'jquery.tabs.min']) {
			resource url: [dir: 'js/jquery', file: name + '.js', plugin: 'p6spy-ui']
		}
	}
}
