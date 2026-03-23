module StoreData
  
  class PropertyGenerator
    
    class << self
      def from(config, model_conf, field_conf)
        type = field_conf.type
        case type
        when 'int', 'date', 'time'
          SimplePropertyGenerator.new(config, model_conf, field_conf, 'int', 'Integer')
        when 'long', 'datetime'
          SimplePropertyGenerator.new(config, model_conf, field_conf, 'long', 'Long')
        when 'double'
          SimplePropertyGenerator.new(config, model_conf, field_conf, 'double', 'Double')
        when 'boolean'
          SimplePropertyGenerator.new(config, model_conf, field_conf, 'boolean', 'Boolean')
        when 'string', 'object-id'
          SimplePropertyGenerator.new(config, model_conf, field_conf, 'String')
        when 'decimal'
          SimplePropertyGenerator.new(config, model_conf, field_conf, 'BigDecimal')
        when 'object'
          ObjectPropertyGenerator.new(config, model_conf, field_conf)
        when 'map'
          MapPropertyGenerator.new(config, model_conf, field_conf)
        when 'std-list'
          StdListPropertyGenerator.new(config, model_conf, field_conf)
        else
          raise ArgumentError, "unsupported field type #{type} on store data"
        end
      end
    end
    
    attr_reader :model_conf, :field_conf
    
    def initialize(config, model_conf, field_conf)
      @config = config
      @model_conf = model_conf
      @field_conf = field_conf
    end
    
    def required?
      @field_conf.required?
    end

    def generate_field_json_annotations_code
      code = ''
      json_libs = @config.json_libs
      if json_libs.include?('Fastjson2')
        code << "        @com.alibaba.fastjson2.annotation.JSONField(name = #{field_conf.store_name_const_name})\n"
      end
      if (json_libs & %w[Jackson Jackson2 Jackson3]).any?
        code << "        @com.fasterxml.jackson.annotation.JsonProperty(#{field_conf.store_name_const_name})\n"
      end
      if json_libs.include?('Jsoniter')
        if @field_conf.type == 'map'
          code << "        @com.jsoniter.annotation.JsonProperty(value = #{field_conf.store_name_const_name}, implementation = LinkedHashMap.class)\n"
        else
          code << "        @com.jsoniter.annotation.JsonProperty(#{field_conf.store_name_const_name})\n"
        end
      end
      code
    end
    
    def generate_field_declaration_code
      raise NotImplementedError, "generate_field_declaration_code is not implemented"
    end
    
    def generate_getter_code
      raise NotImplementedError, "generate_getter_code is not implemented"
    end
    
    def generate_setter_code
      raise NotImplementedError, "generate_setter_code is not implemented"
    end
    
  end
  
end

require_relative 'properties/simple_property_generator'
require_relative 'properties/map_property_generator'
require_relative 'properties/std_list_property_generator'
require_relative 'properties/object_property_generator'
