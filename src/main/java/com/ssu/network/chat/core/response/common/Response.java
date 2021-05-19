package com.ssu.network.chat.core.response.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.GeneratorType;

@Getter
@AllArgsConstructor
public class Response<T> {
    T data;
}
