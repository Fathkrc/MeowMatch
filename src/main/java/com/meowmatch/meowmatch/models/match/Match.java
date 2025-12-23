package com.meowmatch.meowmatch.models.match;

import com.meowmatch.meowmatch.models.Cat;

public record Match(String Id,
                    Cat userCat,
                    String targetCatId,
                    String conversationId) {

}
