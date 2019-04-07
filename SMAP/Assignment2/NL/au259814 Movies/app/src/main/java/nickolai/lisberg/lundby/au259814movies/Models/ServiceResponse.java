package nickolai.lisberg.lundby.au259814movies.Models;

public class ServiceResponse {
    public boolean Success;
    public String Message;

    public ServiceResponse(Boolean success, String message)
    {
        Success = success;
        Message = message;
    }
    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String isMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
