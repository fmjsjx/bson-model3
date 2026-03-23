require_relative 'to_bson_field_value_generator'


class ToBsonValueGenerator

  attr_reader :config, :model_conf, :to_bson_field_value_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @to_bson_field_value_generators = model_conf.fields.filter do |field_conf|
      field_conf.store_field?
    end.map do |field_conf|
      ToBsonFieldValueGenerator.from(field_conf, config, model_conf)
    end
  end

  def generate
    code = ''
    code << "    @Override\n"
    code << "    public BsonDocument toBsonValue() {\n"
    code << "        var _bsonValue = new BsonDocument();\n"
    @to_bson_field_value_generators.each do |to_bson_field_value_generator|
      code << to_bson_field_value_generator.generate
    end
    code << "        return _bsonValue;\n"
    code << "    }\n"
  end

end
