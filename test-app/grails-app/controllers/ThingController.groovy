import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ThingController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Thing.list(params), model:[thingInstanceCount: Thing.count()]
    }

    def show(Thing thingInstance) {
        respond thingInstance
    }

    def create() {
        respond new Thing(params)
    }

    @Transactional
    def save(Thing thingInstance) {
        if (thingInstance == null) {
            notFound()
            return
        }

        if (thingInstance.hasErrors()) {
            respond thingInstance.errors, view:'create'
            return
        }

        thingInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'thing.label', default: 'Thing'), thingInstance.id])
                redirect thingInstance
            }
            '*' { respond thingInstance, [status: CREATED] }
        }
    }

    def edit(Thing thingInstance) {
        respond thingInstance
    }

    @Transactional
    def update(Thing thingInstance) {
        if (thingInstance == null) {
            notFound()
            return
        }

        if (thingInstance.hasErrors()) {
            respond thingInstance.errors, view:'edit'
            return
        }

        thingInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Thing.label', default: 'Thing'), thingInstance.id])
                redirect thingInstance
            }
            '*'{ respond thingInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Thing thingInstance) {

        if (thingInstance == null) {
            notFound()
            return
        }

        thingInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Thing.label', default: 'Thing'), thingInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
