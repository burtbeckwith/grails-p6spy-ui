grails.project.work.dir = 'target'
grails.project.source.level = 1.6

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		compile 'p6spy:p6spy:1.3'
	}

	plugins {
		compile ':google-visualization:0.6'
		compile ':jquery:1.11.0.2'

		build(':release:2.0.4', ':rest-client-builder:1.0.2') {
			export = false
		}
	}
}
