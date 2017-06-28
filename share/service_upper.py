def process(request, response):
    response.body = request.body.upper()
