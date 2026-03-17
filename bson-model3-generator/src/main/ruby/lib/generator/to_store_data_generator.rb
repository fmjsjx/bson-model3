require_relative 'to_store_field_data_generator'


class ToStoreDataGenerator

  attr_reader :config, :model_conf, :field_confs, :to_store_field_data_generators

  def initialize(config, model_conf)
    @config = config
    @model_conf = model_conf
    @field_confs = model_conf.fields.filter { |field_conf| field_conf.store_field? }
    @to_store_field_data_generators = @field_confs.map do |field_conf|
      ToStoreFieldDataGenerator.from(config, model_conf, field_conf)
    end
  end

  def generate
    code = ''
    if @field_confs.any? { |field_conf| field_conf.type == 'map' }
      code << "    @SuppressWarnings(\"unchecked\")\n"
    end
    code << "    @Override\n"
    code << "    public #{@model_conf.name}StoreData toStoreData() {\n"
    code << "        var _storeData = new #{@model_conf.name}StoreData();\n"
    @to_store_field_data_generators.each do |to_store_field_data_generator|
      code << to_store_field_data_generator.generate
    end
    code << "        return _storeData;\n"
    code << "    }\n"
  end

end