package com.meowmatch.meowmatch.models.match;

import com.meowmatch.meowmatch.models.Cat;

public record Match(String Id,Cat targetCat,String conversationId) {

}
