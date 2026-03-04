require_relative 'property_generator'


module StoreData

  class ClassGenerator

    attr_reader :config, :model_conf

    def initialize(config, model_conf)
      @config = config
      @model_conf = model_conf
    end

    def generate
      code = "\n"
      code << generate_class_declaration_code_prefix
      fields = @model_conf.fields.filter { |field| field.store_field? }
      property_generators = fields.map { |field| PropertyGenerator.from(@config, @model_conf, field) }
      property_generators.each { |property_generator| code << property_generator.generate_field_declaration_code }
      property_generators.each do |property_generator|
        code << "\n"
        code << property_generator.generate_getter_code
        code << "\n"
        code << property_generator.generate_setter_code
      end
      code << generate_class_declaration_code_suffix
    end

    private
    def generate_class_declaration_code_prefix
      code = "    @JSONType(alphabetic = false)\n"
      code << "    public static final class #{@model_conf.name}StoreData {\n"
    end

    def generate_class_declaration_code_suffix
      code = "    }\n"
    end
    
  end

end