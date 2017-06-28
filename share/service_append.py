def process(request, response):
    import os
    response.body = "%s %s" % (request.body, os.environ['USER'])
