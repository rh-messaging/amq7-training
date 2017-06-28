def process(request, response):
    response.body = request.body[::-1]
