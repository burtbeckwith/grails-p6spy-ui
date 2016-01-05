import com.p6spy.engine.logging.P6LogFactory
import com.p6spy.engine.outage.P6OutageFactory
import com.p6spy.engine.spy.P6SpyFactory

grails.plugin.p6spy.gsp.layoutAdmin = 'application'
grails.plugin.p6spy.config.excludecategories = ''
// grails.plugin.p6spy.config.excludecategories = 'batch,debug,info,result,resultset' // default
// grails.plugin.p6spy.config.excludecategories = 'batch,commit,debug,error,info,outage,result,resultset,rollback,statement,warn' // all
grails.plugin.p6spy.config.modulelist = [P6SpyFactory, P6LogFactory, P6OutageFactory].collect { it.name }.join(',')
