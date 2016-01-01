package grails.plugin.p6spy.ui

import grails.util.Holders

/**
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class P6SpyTagLib {

	static namespace = 'p6'

	def resources = { attrs ->
		boolean hasResourcesPlugin = Holders.getPluginManager().hasGrailsPlugin('resources')

		if (hasResourcesPlugin) {
			r.require(module: 'p6spy-ui')
			out << r.layoutResources()
		}
		else {
			out << g.javascript(library: 'jquery', plugin: 'jquery')
			for (name in ['table', 'jquery.jdMenu', 'jquery.jdMenu.slate',
			              'tabs', 'tabs-accordion', 'p6spy-ui']) {
				out << """<link rel="stylesheet" media="screen" href="${resource(dir:'css',file:"${name}.css",plugin:'p6spy-ui')}"/>"""
			}
		}
	}

	def layoutResources = { attrs ->
		boolean hasResourcesPlugin = Holders.getPluginManager().hasGrailsPlugin('resources')

		if (hasResourcesPlugin) {
			out << r.layoutResources()
		}
		else {
			for (name in ['jquery/jquery.positionBy', 'jquery/jquery.bgiframe', 'jquery/jquery.jdMenu',
			              'jquery/jquery.dataTables.min', 'jquery/jquery.tabs.min']) {
				out << g.javascript(src: name + '.js', plugin: 'p6spy-ui')
			}
		}
	}
}
