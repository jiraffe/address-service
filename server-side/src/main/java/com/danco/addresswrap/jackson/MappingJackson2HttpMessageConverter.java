package com.danco.addresswrap.jackson;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class MappingJackson2HttpMessageConverter extends AbstractHttpMessageConverter<Object>{

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 
    private ObjectMapper objectMapper = new ObjectMapper();
 
    private boolean prefixJson = false;
 
    public MappingJackson2HttpMessageConverter() {
       super(new MediaType("application", "json", DEFAULT_CHARSET));
    }
 
    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper must not be null");
        this.objectMapper = objectMapper;
    }
 
    public ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
 
    public void setPrefixJson(boolean prefixJson) {
        this.prefixJson = prefixJson;
    }
 
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        JavaType javaType = getJavaType(clazz);
        return (this.objectMapper.canDeserialize(javaType) && canRead(mediaType));
    }
 
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return (this.objectMapper.canSerialize(clazz) && canWrite(mediaType));
    }
 
    @Override
    protected boolean supports(Class<?> clazz) {
        throw new UnsupportedOperationException();
    }
 
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
    throws IOException, HttpMessageNotReadableException {
 
        JavaType javaType = getJavaType(clazz);
        try {
            return this.objectMapper.readValue(inputMessage.getBody(), javaType);
        }
        catch (JsonProcessingException ex) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }
 
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage)
    throws IOException, HttpMessageNotWritableException {
 
        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        JsonGenerator jsonGenerator =
        this.objectMapper.getJsonFactory().createJsonGenerator(outputMessage.getBody(), encoding);
        try {
            if (this.prefixJson) {
                jsonGenerator.writeRaw("{} && ");
            }
            this.objectMapper.writeValue(jsonGenerator, object);
        }
        catch (JsonProcessingException ex) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }
 
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
 
    protected JsonEncoding getJsonEncoding(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            Charset charset = contentType.getCharSet();
            for (JsonEncoding encoding : JsonEncoding.values()) {
                if (charset.name().equals(encoding.getJavaName())) {
                return encoding;
                }
            }
        }
    return JsonEncoding.UTF8;
    }

}
