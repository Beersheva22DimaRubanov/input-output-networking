package telran.net;

public class StringProtocol implements Protocol {

	@Override
	public Response getResponse(Request request) {
		Response res = switch (request.type) {
		case "reverse" -> reverseString(request);
		case "length" -> GetStringLength(request);
		default -> new Response(ResponseCode.WRONG_REQUEST, request.data);
		};
		return res;
	}

	private Response GetStringLength(Request request) {
		Response res = null;
		try {
			res = new Response(ResponseCode.OK, (request.data.toString().length() + ""));
		} catch (Exception e) {
			res = new Response(ResponseCode.WRONG_DATA, request.data);
		}
		return res;
	}

	private Response reverseString(Request request) {
		Response res = null;
		try {
			res = new Response(ResponseCode.OK,
					new StringBuilder(request.data.toString()).reverse().toString());
		} catch (Exception e) {
			res = new Response(ResponseCode.WRONG_DATA, request.data);
		}
		return res;
	}

}
